package com.lecom.storeapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItemRequestDTO {

    @NotNull
    @EqualsAndHashCode.Include
    private Long idProduct;

    @Digits(integer=10, fraction=2)
    @DecimalMin("5.00")
    @Positive
    private BigDecimal unitPrice;

    @NotNull
    private Integer amount;

    @NotEmpty
    private String creationUser;

}
