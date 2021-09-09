package com.lecom.shippingcompany.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderDTO {

    @NotNull
    private Long productId;
    @NotNull
    private String productName;
    @NotNull
    private BigDecimal unitPrice;

}
