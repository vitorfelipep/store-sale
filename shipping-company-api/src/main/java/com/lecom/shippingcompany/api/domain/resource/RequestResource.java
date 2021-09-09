package com.lecom.shippingcompany.api.domain.resource;

import com.lecom.shippingcompany.api.domain.dto.PatchTrackingDTO;
import com.lecom.shippingcompany.api.domain.dto.RequestTrackingDTO;
import com.lecom.shippingcompany.api.domain.dto.TrackingResponseDTO;
import com.lecom.shippingcompany.api.domain.service.RequestTrackingService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/requests/trackings")
@Api(value = "Request")
public class RequestResource {

    private final RequestTrackingService requestTrackingService;

    public RequestResource(RequestTrackingService requestTrackingService) {
        this.requestTrackingService = requestTrackingService;
    }

    @GetMapping("/{trackingCode}/order/{orderId}")
    public ResponseEntity<TrackingResponseDTO> getStatusTracking(
            @PathVariable(name = "trackingCode") final String trackingCode,
            @PathVariable(name = "orderId") final Long orderId
    ) {
        var trackingOrder = requestTrackingService.getStatusTracking(trackingCode, orderId);
        return ResponseEntity.status(HttpStatus.OK).body(trackingOrder);
    }

    @PostMapping
    public ResponseEntity<TrackingResponseDTO> create(
       @Valid @RequestBody RequestTrackingDTO requestTrackingDTO
    ) {
        var trackingOrder = requestTrackingService.createTrackingOrder(requestTrackingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(trackingOrder);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<TrackingResponseDTO> patchTracking(
            @PathVariable(name = "orderId") final Long orderId,
            @Valid @RequestBody final PatchTrackingDTO patchTrackingDTO
    ) {
        var trackingOrder = requestTrackingService.patchTracking(orderId, patchTrackingDTO);
        return ResponseEntity.status(HttpStatus.OK).body(trackingOrder);
    }

}
