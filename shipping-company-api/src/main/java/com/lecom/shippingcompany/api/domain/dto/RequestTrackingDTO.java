package com.lecom.shippingcompany.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RequestTrackingDTO {

    @NotNull
    @EqualsAndHashCode.Include
    private Long orderId;

    @NotNull
    @Digits(integer=10, fraction=2)
    @DecimalMin("5.00")
    @Positive
    private BigDecimal totalOrder;

    @NotNull
    private List<ProductOrderDTO> products;

    @NotNull
    private TargetAddressDTO senderTarget;

    @NotNull
    private TargetAddressDTO addresseeTarget;


}
