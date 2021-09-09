package com.lecom.app.api.catalog.domain.dto;

import com.lecom.app.api.catalog.domain.entity.Catalog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CatalogRequestDTO {

    @EqualsAndHashCode.Include
    private Long id;

    @NotEmpty
    private String name;

    @CNPJ
    @EqualsAndHashCode.Include
    private String cnpj;

    private Boolean status;

    @NotEmpty
    private String creationUser;

    @NotNull
    private Set<ProductRequestDTO> products;

    public CatalogRequestDTO(Catalog catalog) {
        this.id = catalog.getId();
        this.cnpj = catalog.getCnpj();
        this.name = catalog.getName();
        this.status = catalog.getStatus();
        this.creationUser = catalog.getCreationUser();
        this.products = catalog.getProducts()
                .stream().map(ProductRequestDTO::new)
                .collect(Collectors.toSet());
    }
}
