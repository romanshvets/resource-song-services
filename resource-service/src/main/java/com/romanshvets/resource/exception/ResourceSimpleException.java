package com.romanshvets.resource.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceSimpleException extends RuntimeException {

    private final int errorCode;
    private final String errorMessage;

    public ResourceSimpleException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
