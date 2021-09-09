package com.lecom.shippingcompany.api.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "product_tracking")
public class Product {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @NotNull
    @Column(name = "id")
    private Long productId;

    @NotEmpty
    @Column(name = "name")
    private String productName;

    @NotNull
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "tracking_id",
        referencedColumnName = "id",
        nullable = false
    )
    private Tracking tracking;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "change_date")
    private LocalDateTime changeDate;

}
