package com.lecom.shippingcompany.api.domain.service;

import com.lecom.shippingcompany.api.domain.dto.PatchTrackingDTO;
import com.lecom.shippingcompany.api.domain.dto.RequestTrackingDTO;
import com.lecom.shippingcompany.api.domain.dto.TrackingResponseDTO;
import com.lecom.shippingcompany.api.domain.entity.Product;
import com.lecom.shippingcompany.api.domain.entity.TargetAddress;
import com.lecom.shippingcompany.api.domain.entity.Tracking;
import com.lecom.shippingcompany.api.domain.entity.enums.StatusShipping;
import com.lecom.shippingcompany.api.domain.entity.enums.TypeTargetAddress;
import com.lecom.shippingcompany.api.domain.mappers.TrackingMapper;
import com.lecom.shippingcompany.api.domain.repository.TargetAddressRepository;
import com.lecom.shippingcompany.api.domain.repository.TrackingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RequestTrackingService {

    private Logger logger = LoggerFactory.getLogger(RequestTrackingService.class);

    private final TrackingRepository trackingRepository;
    private final TargetAddressRepository targetAddressRepository;
    private final TrackingMapper trackingMapper;

    public RequestTrackingService(
            TrackingRepository trackingRepository,
            TargetAddressRepository targetAddressRepository, TrackingMapper trackingMapper
    ) {
        this.trackingRepository = trackingRepository;
        this.targetAddressRepository = targetAddressRepository;
        this.trackingMapper = trackingMapper;
    }

    public TrackingResponseDTO createTrackingOrder(RequestTrackingDTO requestTrackingDTO) {
        AtomicReference<TrackingResponseDTO> dtoAtomicReference = new AtomicReference();
        trackingRepository.findByOrderId(requestTrackingDTO.getOrderId()).ifPresentOrElse(it -> {
            throwOrderNotFoundExeption(requestTrackingDTO.getOrderId(), "TRACKING-CREATE-ERROR: Already exists a order id with number {} in execution", "Already exists a order id with number %s in execution");

        }, () -> {
            logger.info("TRACKING-ORDER-CREATE: Creating tracking to order {} ", requestTrackingDTO.getOrderId());
            final var tracking = trackingMapper.requestTrackingDtoToTracking(requestTrackingDTO);
            tracking.setTrackindCode(UUID.randomUUID().toString());
            tracking.setStatusShipping(StatusShipping.DEMAND_RECEIVED);
            tracking.getSenderTarget().setTypeTargetAddress(TypeTargetAddress.SENDER);

            var senderAddress = targetAddressRepository.save(tracking.getSenderTarget());
            tracking.setSenderTarget(senderAddress);

            tracking.getAddresseeTarget().setTypeTargetAddress(TypeTargetAddress.ADDRESSEE);
            var targetAddress = targetAddressRepository.save(tracking.getAddresseeTarget());
            tracking.setAddresseeTarget(targetAddress);
            var products = tracking.getProducts();
            tracking.setProducts(null);
            var trackingSaved = trackingRepository.save(tracking);
            trackingSaved.setProducts(products);
            tracking.getProducts().forEach(product -> product.setTracking(tracking));
            trackingRepository.save(tracking);
            getTrackingResponse(dtoAtomicReference, tracking);
        });

        return dtoAtomicReference.get();
    }

    public TrackingResponseDTO patchTracking(
        final Long orderId,
        final PatchTrackingDTO patchTrackingDTO
    ) {
        AtomicReference<TrackingResponseDTO> dtoAtomicReference = new AtomicReference();
        trackingRepository.findByOrderId(orderId).ifPresentOrElse(tracking -> {
            tracking.setStatusShipping(patchTrackingDTO.getStatusShipping());
            trackingRepository.save(tracking);
            getTrackingResponse(dtoAtomicReference, tracking);
        }, () -> {
            throwOrderNotFoundExeption(
                    orderId,
                    "TRACKING-PATCH-ERROR: Tracking not found with order id {}",
                    "Tracking not found with order id  %s.");
        });

        return dtoAtomicReference.get();
    }

    public TrackingResponseDTO getStatusTracking(String trackingCode, Long orderId) {
        AtomicReference<TrackingResponseDTO> dtoAtomicReference = new AtomicReference();
        trackingRepository.findByOrderIdAndTrackindCode(orderId, trackingCode).ifPresentOrElse(tracking -> {
            getTrackingResponse(dtoAtomicReference, tracking);
        }, () ->  throwOrderNotFoundExeption(
                    orderId,
                    "TRACKING-PATCH-ERROR: Tracking not found with order id {}",
                    "Tracking not found with order id  %s."));

        return dtoAtomicReference.get();
    }

    private void getTrackingResponse(
        AtomicReference<TrackingResponseDTO> dtoAtomicReference,
        Tracking tracking
    ) {
        dtoAtomicReference.set(
                TrackingResponseDTO.builder()
                        .trackindCode(tracking.getTrackindCode())
                        .statusShipping(tracking.getStatusShipping())
                        .build()
        );
    }

    private void throwOrderNotFoundExeption(Long orderId, String s, String s2) {
        logger.error(s, orderId);
        throw new RuntimeException(
                String.format(s2, orderId));
    }

}
