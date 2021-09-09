package com.lecom.app.api.catalog.domain.service.exception;

import java.io.Serial;
import java.io.Serializable;

public class CatalogNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 5813194902329900540L;

    public CatalogNotFoundException(String message) {
        super(message);
    }

}
