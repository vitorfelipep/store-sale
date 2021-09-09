package com.lecom.storeapp.domain.dto;

import com.lecom.storeapp.domain.enums.StatusShipping;
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
