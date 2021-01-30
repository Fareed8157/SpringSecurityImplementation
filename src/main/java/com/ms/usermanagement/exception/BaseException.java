package com.ms.usermanagement.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private String timestamp;
    private String message;
    private int status;
    private List<Map<String, String>> errors;

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

}
