package com.lecom.storeapp.domain.dto;

import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StoreRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3417205649041661853L;

    @NotEmpty
    private String name;

    @CNPJ
    @NotEmpty
    private String cnpj;

    private Boolean status = true;

    @NotNull
    @Positive
    private Integer cep;

    @NotEmpty
    private String street;

    @NotEmpty
    private String addressNumber;

    @NotEmpty
    private String city;

    @NotEmpty
    private String state;

    @NotEmpty
    private String country;

    private String referencePoint;

    @NotEmpty
    private String creationUser;

}
