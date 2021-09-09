package com.lecom.storeapp.domain.dto;

import com.lecom.storeapp.domain.enums.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderViewDTO {

    @EqualsAndHashCode.Include
    private Long id;

    private Long employeeId;

    private Long customerId;

    private StoreViewtDTO storeViewtDTO;

    private OrderStatus orderStatus;

    private BigDecimal totalOrder = BigDecimal.ZERO;

    private String trackingCode;

    private LocalDateTime orderDate;

    private LocalDateTime creationDate;

    private LocalDateTime changeDate;

    private String creationUser;

    private String changeUser;

}
