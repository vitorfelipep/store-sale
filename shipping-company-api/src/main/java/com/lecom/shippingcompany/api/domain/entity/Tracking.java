package com.lecom.shippingcompany.api.domain.entity;

import com.lecom.shippingcompany.api.domain.entity.enums.StatusShipping;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
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
@Table(name = "tracking_order")
public class Tracking {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @EqualsAndHashCode.Include
    @Column(name = "order_id")
    private Long orderId;

    @NotEmpty
    @EqualsAndHashCode.Include
    @Column(name = "tracking_code")
    private String trackindCode;

    @NotNull
    @Column(name = "status_shipping")
    @Enumerated(EnumType.STRING)
    private StatusShipping statusShipping;

    @NotNull
    @Digits(integer=10, fraction=2)
    @DecimalMin("5.00")
    @Positive
    @Column(name = "total_order")
    private BigDecimal totalOrder;

    @NotNull
    @OneToOne
    @JoinColumn(name = "sender_id")
    private TargetAddress senderTarget;

    @NotNull
    @OneToOne
    @JoinColumn(name = "addressee_id")
    private TargetAddress addresseeTarget;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "change_date")
    private LocalDateTime changeDate;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "tracking",
        orphanRemoval = true,
        cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    private List<Product> products;

}
