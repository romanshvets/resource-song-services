package com.romanshvets.song.config.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SongSimpleException extends RuntimeException {

    private final int errorCode;
    private final String errorMessage;

    public SongSimpleException(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
