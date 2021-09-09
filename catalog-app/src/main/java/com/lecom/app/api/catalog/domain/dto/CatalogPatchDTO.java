package com.lecom.app.api.catalog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CatalogPatchDTO {

    @NotEmpty
    private String name;

    @CNPJ
    @EqualsAndHashCode.Include
    private String cnpj;

    private Boolean status;

    @NotEmpty
    private String creationUser;

}
