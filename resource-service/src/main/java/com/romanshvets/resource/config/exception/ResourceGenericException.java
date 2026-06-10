package com.romanshvets.resource.config.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceGenericException extends RuntimeException {

    private final int errorCode;
    private final String errorMessage;

    public ResourceGenericException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
