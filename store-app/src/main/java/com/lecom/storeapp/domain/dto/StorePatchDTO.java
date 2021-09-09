package com.lecom.storeapp.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorePatchDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 6830959856061308702L;

    @NotEmpty
    private String name;

    private Boolean status = true;

    @NotNull
    private Integer cep;

    @NotEmpty
    private String street;

    @NotEmpty
    private String number;

    @NotEmpty
    private String city;

    @NotEmpty
    private String state;

    @NotEmpty
    private String country;

    private String referencePoint;

    @NotEmpty
    private String changeUser;

}
