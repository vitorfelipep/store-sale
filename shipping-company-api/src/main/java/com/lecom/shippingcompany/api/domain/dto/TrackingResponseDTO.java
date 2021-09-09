package com.lecom.shippingcompany.api.domain.dto;

import com.lecom.shippingcompany.api.domain.entity.enums.StatusShipping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackingResponseDTO {

    private String trackindCode;
    private StatusShipping statusShipping;

}
