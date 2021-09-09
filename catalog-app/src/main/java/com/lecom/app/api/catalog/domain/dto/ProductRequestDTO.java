package com.lecom.app.api.catalog.domain.dto;

import com.lecom.app.api.catalog.domain.dto.enums.Category;
import com.lecom.app.api.catalog.domain.entity.Product;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7332910007595478771L;

    @NotEmpty
    private String name;

    @NotNull
    private Category category;

    @NotNull
    private BigDecimal unitPrice;

    private Boolean status = true;

    @NotEmpty
    private String creationUser;

    public ProductRequestDTO(Product product) {
        this.name = product.getName();
        this.category = product.getCategory();
        this.status = product.getStatus();
        this.creationUser = product.getCreationUser();
    }

}
