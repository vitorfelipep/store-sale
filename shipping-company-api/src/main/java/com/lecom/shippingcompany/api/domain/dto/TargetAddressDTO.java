package com.lecom.shippingcompany.api.domain.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TargetAddressDTO {

    @NotEmpty
    @EqualsAndHashCode.Include
    private String cnpjOrCpf;

    @NotEmpty
    private String name;

    @NotNull
    private Integer cep;

    @NotEmpty
    private String street;

    @NotEmpty
    private String number;

    @NotEmpty
    private String city;

    @NotEmpty
    private String state;

    @NotEmpty
    private String country;

    private String referencePoint;

}
