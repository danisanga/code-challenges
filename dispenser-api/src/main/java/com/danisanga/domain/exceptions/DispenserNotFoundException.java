package com.danisanga.domain.exceptions;

public class DispenserNotFoundException extends DispenserWebServiceException{
    public DispenserNotFoundException(final String errorMessage) {
        super(errorMessage);
    }
}
