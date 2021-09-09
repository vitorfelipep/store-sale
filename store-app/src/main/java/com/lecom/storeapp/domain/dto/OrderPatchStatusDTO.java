package com.lecom.storeapp.domain.dto;

import com.lecom.storeapp.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPatchStatusDTO {

    @NotNull
    private OrderStatus orderStatus;

}
