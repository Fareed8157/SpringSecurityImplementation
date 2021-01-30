package com.ms.usermanagement.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class BaseExceptionTemplate {

    private String timestamp;
    private String message;
    private int status;
    private List<Map<String, String>> errors;

    public BaseExceptionTemplate(String timestamp, String message, int status, List<Map<String, String>> errors) {

        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
        this.errors = errors;
    }
}
