package com.lecom.storeapp.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

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

}
