package com.lecom.storeapp.domain.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StoreItemsViewtDTO {

    @EqualsAndHashCode.Include
    private Long id;

    @NotEmpty
    private String name;

    private String cnpj;

    private List<ProductViewDTO> items;

}
