package com.lecom.storeapp.domain.dto;

import com.lecom.storeapp.domain.dto.enums.Category;
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

    private Boolean status;

    private LocalDateTime creationDate;

    private LocalDateTime changeDate;

    private String creationUser;

    private String changeUser;

}
