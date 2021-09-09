package com.lecom.storeapp.domain.service.exception;

import java.io.Serial;
import java.io.Serializable;

public class StoreAlreadyExistsException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 5813194902329900540L;

    public StoreAlreadyExistsException(String message) {
        super(message);
    }
}
