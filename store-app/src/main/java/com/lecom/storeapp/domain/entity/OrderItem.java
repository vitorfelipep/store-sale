package com.lecom.storeapp.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "order_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Order order;

    @NotNull
    @Column(name = "product_id")
    private Long idProduct;

    @Digits(integer=10, fraction=2)
    @DecimalMin("5.00")
    @Positive
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Transient
    private Integer amount;

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

}
