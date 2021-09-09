package com.lecom.storeapp.domain.resource;

import com.lecom.storeapp.domain.dto.*;
import com.lecom.storeapp.domain.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/v1/orders")
@Api(value = "Orders resource")
public class OrderResource {

    final OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @ApiOperation(value = "Busca todos os pedidos cadastrados no sistema com filtros **search (customerId) com ordenação e paginação", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista de pedidos com paginação", response = Page.class),
            @ApiResponse(code = 404, message = "order.not.found")
    })
    public Page<OrderViewDTO> findAllOrders(
        @RequestParam(value = "customer") Long customer,
        @RequestParam(value = "orderDateAt", required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime orderDateAt,
        @RequestParam(value = "orderDateUntil", required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime orderDateUntil,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "5") Integer size,
        @RequestParam(value = "sort", defaultValue = "id") String sort,
        @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        return orderService.findAllFiltering(customer, orderDateAt, orderDateUntil, page, size, sort, direction);
    }

    @GetMapping("{orderId}/items")
    @ApiOperation(value = "Busca todos os pedidos cadastrados no sistema com filtros **search (customerId) com ordenação e paginação", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma lista de pedidos com paginação", response = Page.class),
            @ApiResponse(code = 404, message = "order.not.found")
    })
    public Page<OrderItemViewDTO> findAllOrders(
            @PathVariable(value = "orderId") Long orderId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        return orderService.findAllItemsByOrderId(orderId, page, size, sort, direction);
    }

    @PostMapping
    public ResponseEntity create(
            @Valid @RequestBody OrderRequestDTO orderRequestDTO
    ) {
        final URI uri  = orderService.create(orderRequestDTO);
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{orderId}/delivery/status")
    @ApiOperation(value = "Atualiza os status do pedido no sistema", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Atualiza os status do pedido no sistema", response = Page.class),
            @ApiResponse(code = 404, message = "order.not.found")
    })
    public ResponseEntity<OrderViewDTO> patchOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderPatchStatusDTO orderStatus
    ) {
        final var orderItemViewDTO = orderService.patchOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(orderItemViewDTO);
    }

    @GetMapping("/{orderId}/tracking/{trackingCode}/status")
    @ApiOperation(value = "Busca os status do pedido no sistema", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Busca os status do pedido no sistema", response = Page.class),
            @ApiResponse(code = 404, message = "order.not.found")
    })
    public ResponseEntity<TrackingResponseDTO> getTrackingStatusFromOrderId(
            @PathVariable Long orderId,
            @PathVariable String trackingCode
    ) {
        final var trackingResponseDTO = orderService.getStatusOfTrackingByOrderIdAndTrackingCode(orderId, trackingCode);
        return ResponseEntity.ok(trackingResponseDTO);
    }

}
