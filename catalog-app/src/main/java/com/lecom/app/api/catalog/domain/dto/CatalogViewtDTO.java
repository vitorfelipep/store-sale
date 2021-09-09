package com.lecom.app.api.catalog.domain.dto;

import com.lecom.app.api.catalog.domain.entity.Catalog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CatalogViewtDTO {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    @EqualsAndHashCode.Include
    private String cnpj;

    private Boolean status;

    private LocalDateTime creationDate;

    private LocalDateTime changeDate;

    private String creationUser;

    private String changeUser;

    private Set<ProductViewDTO> products;

    public CatalogViewtDTO(Catalog catalog) {
        this.id = catalog.getId();
        this.cnpj = catalog.getCnpj();
        this.name = catalog.getName();
        this.status = catalog.getStatus();
        this.creationDate = catalog.getCreationDate();
        this.changeDate = catalog.getChangeDate();
        this.creationUser = catalog.getCreationUser();
        this.changeUser = catalog.getChangeUser();
        this.products = catalog.getProducts()
                .stream().map(ProductViewDTO::new)
                .collect(Collectors.toSet());
    }
}
