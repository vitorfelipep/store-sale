package com.lecom.storeapp.domain.service.exception;

import java.io.Serial;
import java.io.Serializable;

public class StoreNotFoundException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 5813194902329900540L;

    public StoreNotFoundException(String message) {
        super(message);
    }
}
