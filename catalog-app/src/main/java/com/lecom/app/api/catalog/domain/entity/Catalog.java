package com.lecom.app.api.catalog.domain.entity;

import com.lecom.app.api.catalog.domain.dto.CatalogRequestDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "store_catalog")
public class Catalog {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    @CNPJ
    @EqualsAndHashCode.Include
    @Column(name = "cnpj", length = 14)
    private String cnpj;

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

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "catalog",
            cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    private Set<Product> products;

    public Catalog(CatalogRequestDTO catalog) {
        this.id = catalog.getId() != null ? catalog.getId() : null;
        this.name = catalog.getName();
        this.cnpj = catalog.getCnpj();
        this.status = catalog.getStatus();
        this.creationUser = catalog.getCreationUser();
        this.products = catalog.getProducts()
                .stream().map(it -> new Product(it, this)).collect(Collectors.toSet());
    }
}
