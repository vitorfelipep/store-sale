package com.lecom.storeapp.domain.dto;

import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RequestTrackingDTO {

    @NotNull
    @EqualsAndHashCode.Include
    private Long orderId;

    private BigDecimal totalOrder;

    private List<ProductOrderDTO> products;

    private TargetAddressDTO senderTarget;

    private TargetAddressDTO addresseeTarget;


}
