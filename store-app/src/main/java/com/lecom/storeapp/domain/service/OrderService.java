package com.lecom.storeapp.domain.service;

import com.lecom.storeapp.domain.dto.*;
import com.lecom.storeapp.domain.entity.Order;
import com.lecom.storeapp.domain.entity.OrderItem;
import com.lecom.storeapp.domain.enums.OrderStatus;
import com.lecom.storeapp.domain.mappers.OrderItemMapper;
import com.lecom.storeapp.domain.mappers.OrderMapper;
import com.lecom.storeapp.domain.repository.OrderItemRepository;
import com.lecom.storeapp.domain.repository.OrderRepository;
import com.lecom.storeapp.domain.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final StoreRepository storeRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final String trackingUrl;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            StoreRepository storeRepository,
            OrderMapper orderMapper,
            OrderItemMapper orderItemMapper,
            @Value("${apps.tracking.order.uri}")
            String trackingUrl
    ) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.storeRepository = storeRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.trackingUrl = trackingUrl;
    }

    public URI create(OrderRequestDTO orderRequestDTO) {
        AtomicReference<Order> orderAtomicReference = new AtomicReference();
        logger.info("STORE-ORDER-CREATE: receiving a new order {} from store id {}", orderRequestDTO, orderRequestDTO.getStoreId());
        storeRepository.findById(orderRequestDTO.getStoreId()).ifPresentOrElse(store -> {
            logger.info("STORE-ORDER-CREATE: Store {} found with id {}", store.getName(), orderRequestDTO.getStoreId());
            Order order = orderMapper.orderRequestDtoToOrder(orderRequestDTO);
            BigDecimal totalOrder = BigDecimal.valueOf(order.getItems().stream().mapToDouble(
                    it -> it.getUnitPrice().multiply(BigDecimal.valueOf(it.getAmount())).doubleValue()).sum());
            if (totalOrder.equals(order.getTotalOrder())) {
                order.setStore(store);
                order.getItems().forEach(it -> it.setOrder(order));
                logger.info("STORE-ORDER-CREATE: Save order", order);
                orderAtomicReference.set(orderRepository.save(order));

                // Aqui após salvar disparar um evento no tópico kafka se der tempo...fazer
            } else {
                logger.error("STORE-CREATE-ERROR: Total order don't match. Value passed: {} but must being: {}", order.getTotalOrder(), totalOrder);
                throw new RuntimeException(
                        String.format("Total order don't match. Value passed: %s but must being: %s", order.getTotalOrder(), totalOrder));
            }
        }, () -> {
            logger.error("STORE-CREATE-ERROR: Store not found with storeId: {}", orderRequestDTO.getStoreId());
            throw new EmptyResultDataAccessException(String.format("No store with id %s were found.", orderRequestDTO.getStoreId()), 1);
        });

        return ServletUriComponentsBuilder
                .fromPath("/v1/orders")
                .path("/{id}")
                .build(orderAtomicReference.get().getId());
    }

    public Page<OrderViewDTO> findAllFiltering(
            final Long customer,
            final LocalDateTime orderDateAt,
            final LocalDateTime orderDateUntil,
            final Integer page,
            final Integer size,
            final String sort,
            final String direction
    ) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort).toOptional();
        Page<Order> orderPage;
        if (null != customer
            && null == orderDateAt && null == orderDateUntil) {
            orderPage = orderRepository.findAllByFiltering(
                    customer, pageable.get());

        } else if (null != customer
                && null != orderDateAt && null != orderDateUntil) {
            orderPage = orderRepository.findOrdersByEmployeeAndWithDateRange(
                    customer, orderDateAt, orderDateUntil, pageable.get());
        } else {
            orderPage = orderRepository.findAll(pageable.get());
        }
        if (orderPage.hasContent()) {
            final var orderList = orderPage.getContent()
                    .stream().map(it -> orderMapper.orderToOrderViewDTO(it))
                    .collect(Collectors.toList());
            return new PageImpl<>(
                    orderList,
                    orderPage.getPageable(),
                    orderPage.getTotalElements()
            );
        }
        return Page.empty();
    }

    public Page<OrderItemViewDTO> findAllItemsByOrderId(
            Long orderId,
            Integer page,
            Integer size,
            String sort,
            String direction
    ) {
        Optional<Pageable> pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sort).toOptional();
        Page<OrderItem> orderItemsPage;
        if (orderId != null) {
            orderItemsPage = orderItemRepository.findAllByOrderId(orderId, pageable.get());
            if (orderItemsPage.hasContent()) {
                final var orderList = orderItemsPage.getContent()
                        .stream().map(it -> orderItemMapper.orderToOrderViewDTO(it))
                        .collect(Collectors.toList());
                return new PageImpl<>(
                        orderList,
                        orderItemsPage.getPageable(),
                        orderItemsPage.getTotalElements()
                );
            }
        }

        return Page.empty();
    }

    public OrderViewDTO patchOrderStatus(Long orderId, OrderPatchStatusDTO orderStatus) {
        logger.info("STORE-ORDER-PATCH: receiving a orderStatus {} from order id {}", orderStatus, orderId);
        AtomicReference<OrderViewDTO> orderAtomicReference = new AtomicReference();
        orderRepository.findById(orderId).ifPresentOrElse(order -> {
            order.setOrderStatus(orderStatus.getOrderStatus());
            getTrackingCodeIfStatusIsWaitingShipment(orderId, orderStatus, order);
            var orderUpdated = orderRepository.save(order);
            orderAtomicReference.set(orderMapper.orderToOrderViewDTO(orderUpdated));
        }, () -> {
            logger.error("STORE-PATCH-ERROR: Order not found with storeId: {}", orderId);
            throw new EmptyResultDataAccessException(String.format("No orders with id %s were found.", orderId), 1);
        });

        return orderAtomicReference.get();
    }

    public TrackingResponseDTO getStatusOfTrackingByOrderIdAndTrackingCode(Long orderId, String trackingCode) {
        logger.info("STORE-ORDER-GET-STATUS: receiving a order id {}", orderId);
        AtomicReference<TrackingResponseDTO> orderAtomicReference = new AtomicReference();
        orderRepository.findByIdAndTrackingCode(orderId, trackingCode).ifPresentOrElse(order -> {
            RestTemplate restTemplate = new RestTemplate();
            var trackingStatusGetUrl = trackingUrl.concat(
                    String.format("/%s/order/%s", trackingCode, orderId));

            logger.info("STORE-ORDER-GET-STATUS: searching status of tracking of order id {}", orderId);
            ResponseEntity<TrackingResponseDTO> result = restTemplate
                    .getForEntity(trackingStatusGetUrl, TrackingResponseDTO.class);

            if (result.getStatusCode().is2xxSuccessful()) {
                logger.info("STORE-ORDER-GET-STATUS: receiving status of tracking of order id {}", orderId);
                var trackingResponseDTO = result.getBody();
                orderAtomicReference.set(trackingResponseDTO);
            }
        }, () -> {
            logger.error("STORE-PATCH-ERROR: Order not found with storeId: {}", orderId);
            throw new EmptyResultDataAccessException(String.format("No orders with id %s were found.", orderId), 1);
        });
        logger.info("STORE-ORDER-GET-STATUS: returning found status of tracking of order id {} - Tracking Response: {}", orderId, orderAtomicReference.get());
        return orderAtomicReference.get();
    }

    private void getTrackingCodeIfStatusIsWaitingShipment(Long orderId, OrderPatchStatusDTO orderStatus, Order order) {
        if (orderStatus.getOrderStatus().equals(OrderStatus.AWAITING_SHIPMENT)) {
            var requestTrackingDTO = RequestTrackingDTO.builder()
                    .orderId(orderId)
                    .totalOrder(order.getTotalOrder())
                    .products(getProductOrderDTOS(order))
                    .addresseeTarget(getAddressDTOBuilder().build())
                    .senderTarget(getTargetAddressDTO(order))
                    .build();
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<TrackingResponseDTO> result = restTemplate
                    .postForEntity(trackingUrl, requestTrackingDTO, TrackingResponseDTO.class);
            if (result.getStatusCode().is2xxSuccessful()) {
                var trackingResponseDTO = result.getBody();
                order.setTrackingCode(trackingResponseDTO.getTrackindCode());
            }
        }
    }

    private TargetAddressDTO getTargetAddressDTO(Order order) {
        return TargetAddressDTO.builder()
                .cnpjOrCpf(order.getStore().getCnpj())
                .name(order.getStore().getName())
                .street(order.getStore().getStreet())
                .cep(order.getStore().getCep())
                .city(order.getStore().getCity())
                .country(order.getStore().getCountry())
                .number(order.getStore().getAddressNumber())
                .state(order.getStore().getState())
                .referencePoint(order.getStore().getReferencePoint())
                .build();
    }

    private TargetAddressDTO.TargetAddressDTOBuilder getAddressDTOBuilder() {
        //TODO integrar com api de cliente
        return TargetAddressDTO.builder()
                .cnpjOrCpf("08050079031")
                .cep(26789855)
                .city("Rio de Janeiro")
                .country("Brazil")
                .name("João da Couves")
                .number("25")
                .state("RJ")
                .street("Rua general doutor estranho")
                .referencePoint("Ao lado da loja gaste aqui");
    }

    private List<ProductOrderDTO> getProductOrderDTOS(Order order) {
        return order.getItems().stream().map(orderItem -> ProductOrderDTO.builder()
                .productId(orderItem.getIdProduct())
                // TODO Integrar com a api de produto
                .productName("Teste")
                .unitPrice(orderItem.getUnitPrice())
                .build()).collect(Collectors.toList());
    }


}
