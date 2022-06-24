package com.meli.mutant.exception;

import org.springframework.http.HttpStatus;

public class InvalidStringParametersException extends DnaException{
    private static final String error = "dna.invalid.string.parameter";

    public InvalidStringParametersException(String dnaRow) {
        super(error, HttpStatus.BAD_REQUEST, "Only valid characters are (A,T,C,G). Found invalid chars in " + dnaRow);
    }
}
