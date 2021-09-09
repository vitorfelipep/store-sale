package com.lecom.app.api.catalog.domain.dto;

import com.lecom.app.api.catalog.domain.dto.enums.Category;
import com.lecom.app.api.catalog.domain.entity.Product;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductViewDTO {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private Category category;

    private BigDecimal unitPrice;

    private Boolean status = true;

    private LocalDateTime creationDate;

    private LocalDateTime changeDate;

    private String creationUser;

    private String changeUser;

    public ProductViewDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.unitPrice = product.getUnitPrice();
        this.category = product.getCategory();
        this.status = product.getStatus();
        this.creationDate = product.getCreationDate();
        this.changeDate = product.getChangeDate();
        this.creationUser = product.getCreationUser();
        this.changeUser = product.getChangeUser();
    }
}
