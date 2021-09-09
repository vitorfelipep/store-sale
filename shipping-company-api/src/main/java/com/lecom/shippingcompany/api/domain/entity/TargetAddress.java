package com.lecom.shippingcompany.api.domain.entity;

import com.lecom.shippingcompany.api.domain.entity.enums.TypeTargetAddress;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "target_address")
public class TargetAddress {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @NotEmpty
    @EqualsAndHashCode.Include
    @Column(name = "cnpj_cpf")
    private String cnpjOrCpf;

    @NotEmpty
    private String name;

    @NotNull
    @Column(name = "type_target_address")
    @Enumerated(EnumType.STRING)
    private TypeTargetAddress typeTargetAddress;

    @NotNull
    private Integer cep;

    @NotEmpty
    private String street;

    @NotEmpty
    @Column(name = "address_number")
    private String number;

    @NotEmpty
    private String city;

    @NotEmpty
    @Column(name = "state_address")
    private String state;

    @NotEmpty
    private String country;

    @Column(name = "reference_point")
    private String referencePoint;

}
