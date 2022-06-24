package com.meli.mutant.exception;

import org.springframework.http.HttpStatus;

public class InvalidParametersException extends DnaException{
    private static final String error = "dna.invalid.parameters";

    public InvalidParametersException(String errorMessage) {
        super(error, HttpStatus.BAD_REQUEST, "The parameter content is not valid " + errorMessage);
    }
}
