package com.romanshvets.resource.config.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ResourceValidationException extends ResourceGenericException {

    private final Map<String, String> details;

    public ResourceValidationException(int errorCode, String errorMessage, Map<String, String> details) {
        super(errorCode, errorMessage);

        this.details = details;
    }
}
