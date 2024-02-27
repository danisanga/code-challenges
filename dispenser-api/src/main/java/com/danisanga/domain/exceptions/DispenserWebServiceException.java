package com.danisanga.domain.exceptions;

public class DispenserWebServiceException extends RuntimeException {
    public DispenserWebServiceException(final String errorMessage) {
        super(errorMessage);
    }
}
