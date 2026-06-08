package com.romanshvets.song.config.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SongValidationException extends SongSimpleException {

    private final Map<String, String> details;

    public SongValidationException(int errorCode, String errorMessage, Map<String, String> details) {
        super(errorCode, errorMessage);

        this.details = details;
    }
}
