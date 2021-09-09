package com.lecom.storeapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItemViewDTO {

    private Long id;

    private OrderViewDTO orderViewDTO;

    private Long idProduct;

    private BigDecimal unitPrice;

    private Integer amount;

    private LocalDateTime creationDate;

    private LocalDateTime changeDate;

    private String creationUser;

    private String changeUser;

}
