package com.buezman.buezBlogRestApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CredentialAlreadyExistsException extends RuntimeException{

    public CredentialAlreadyExistsException(String resourceName, String fieldValue) {
        super(String.format("%s '%s' already exists", resourceName, fieldValue));
    }
}
