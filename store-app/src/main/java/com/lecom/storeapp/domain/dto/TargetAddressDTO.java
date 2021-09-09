package com.lecom.storeapp.domain.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TargetAddressDTO {

    @EqualsAndHashCode.Include
    private String cnpjOrCpf;

    private String name;

    private Integer cep;

    private String street;

    private String number;

    private String city;

    private String state;

    private String country;

    private String referencePoint;

}
