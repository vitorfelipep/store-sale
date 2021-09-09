package com.lecom.storeapp.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long storeId;

    @NotEmpty
    private String name;

    @CNPJ
    @NotEmpty
    private String cnpj;

    private Boolean status = true;

    @NotNull
    private Integer cep;

    @NotEmpty
    private String street;

    @NotEmpty
    @Column(name = "address_number")
    private String addressNumber;

    @NotEmpty
    private String city;

    @NotEmpty
    @Column(name = "state_address")
    private String state;

    @NotEmpty
    private String country;

    @Column(name = "reference_point")
    private String referencePoint;

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
