package com.lecom.app.api.catalog.domain.service.exception;

import java.io.Serial;
import java.io.Serializable;

public class CatalogAlreadyExistsException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = -5524800238675109721L;

    public CatalogAlreadyExistsException(String message) {
        super(message);
    }

}
