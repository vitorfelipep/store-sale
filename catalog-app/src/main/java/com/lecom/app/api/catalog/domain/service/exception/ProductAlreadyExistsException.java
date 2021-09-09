package com.lecom.app.api.catalog.domain.service.exception;

import java.io.Serial;
import java.io.Serializable;

public class ProductAlreadyExistsException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = -3030808488403148293L;

    public ProductAlreadyExistsException(String message) {
        super(message);
    }

}
