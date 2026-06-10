package com.romanshvets.song.config.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SongGenericException extends RuntimeException {

    private final int errorCode;
    private final String errorMessage;

    public SongGenericException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
