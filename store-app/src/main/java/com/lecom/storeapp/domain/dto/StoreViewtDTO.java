package com.lecom.storeapp.domain.dto;

import com.lecom.storeapp.domain.entity.Store;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StoreViewtDTO {

    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    @EqualsAndHashCode.Include
    private String cnpj;

    private Boolean status;

    private Integer cep;

    private String street;

    private String number;

    private String city;

    private String state;

    private String country;

    private String referencePoint;

    private LocalDateTime creationDate;

    private String creationUser;

    public StoreViewtDTO(Store store) {
        this.id  = store.getStoreId();
        this.name = store.getName();
        this.cnpj = store.getCnpj();
        this.status = store.getStatus();
        this.cep = store.getCep();
        this.street = store.getStreet();
        this.number = store.getAddressNumber();
        this.city = store.getCity();
        this.state = store.getState();
        this.country = store.getCountry();
        this.referencePoint = store.getReferencePoint();
        this.creationDate = store.getCreationDate();
        this.creationUser = store.getCreationUser();
    }
}
