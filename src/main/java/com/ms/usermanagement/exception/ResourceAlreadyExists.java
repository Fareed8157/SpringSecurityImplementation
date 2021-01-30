package com.ms.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExists extends BaseException {
    public ResourceAlreadyExists(String message) {
        super(message);
    }
}
