package com.lecom.storeapp.domain.dto;

import com.lecom.storeapp.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    @NotNull
    private Long employeeId;

    @NotNull
    private Long customerId;

    @NotNull
    private Long inventoryId;

    @NotNull
    private Long storeId;

    @NotNull
    private OrderStatus orderStatus;

    @NotNull
    @Digits(integer=10, fraction=2)
    @DecimalMin("5.00")
    @Positive
    private BigDecimal totalOrder;

    @NotEmpty
    private String creationUser;

    @NotNull
    List<OrderItemRequestDTO> items;

}
