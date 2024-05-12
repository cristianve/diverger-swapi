package com.diverger.swapi.domain.exception;

public class PersonNotFoundException extends RuntimeException {

    public static final String PERSON_NOT_FOUND = "Person not found";

    public PersonNotFoundException() {
        super(PERSON_NOT_FOUND);
    }
}