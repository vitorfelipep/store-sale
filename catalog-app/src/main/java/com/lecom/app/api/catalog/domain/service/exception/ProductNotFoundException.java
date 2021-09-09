package com.lecom.app.api.catalog.domain.service.exception;

import java.io.Serial;
import java.io.Serializable;

public class ProductNotFoundException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = -1551364023568923744L;

    public ProductNotFoundException(String message) {
        super(message);
    }

}
