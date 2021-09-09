package com.lecom.storeapp.domain.entity;

import com.lecom.storeapp.domain.enums.OrderStatus;
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
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "store_order")
public class Order {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @Column(name = "employee_id")
    @EqualsAndHashCode.Include
    private Long employeeId;

    @NotNull
    @Column(name = "customer_id")
    @EqualsAndHashCode.Include
    private Long customerId;

    @NotNull
    @Column(name = "inventory_id")
    private Long inventoryId;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "status_order")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @NotNull
    @Digits(integer=10, fraction=2)
    @DecimalMin("5.00")
    @Positive
    @Column(name = "amount")
    private BigDecimal totalOrder = BigDecimal.ZERO;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "order",
        orphanRemoval = true,
        cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    private List<OrderItem> items;

    @Column(name = "tracking_code")
    private String trackingCode;

    @CreationTimestamp
    @Column(name = "order_date", updatable = false)
    private LocalDateTime orderDate;

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
