package com.meli.mutant.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class DnaException  extends  RuntimeException{
    private HttpStatus status;
    private String error;
    private String errorMessage;

    public DnaException(String error, HttpStatus status, String errorMessage) {
        super(error);
        this.error = error;
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
