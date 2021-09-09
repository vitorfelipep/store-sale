package com.lecom.app.api.catalog.domain.entity;


import com.lecom.app.api.catalog.domain.dto.ProductRequestDTO;
import com.lecom.app.api.catalog.domain.dto.enums.Category;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = -7937828866102870532L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;

    @Digits(integer=6, fraction=2)
    @DecimalMin("5.00")
    @Column(name = "price")
    private BigDecimal unitPrice;

    private Boolean status = true;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "change_date")
    private LocalDateTime changeDate;

    @Column(name = "creation_user", updatable = false)
    private String creationUser;

    @Column(name = "change_user")
    private String changeUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    public Product(ProductRequestDTO productDTO, Catalog catalog) {
        this.name = productDTO.getName();
        this.category = productDTO.getCategory();
        this.unitPrice = productDTO.getUnitPrice();
        this.status = productDTO.getStatus();
        this.creationUser = productDTO.getCreationUser();
        this.catalog = catalog;
    }
}
