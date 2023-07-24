package com.example.messageSource.exception;

import com.example.messageSource.model.ErrorDetails;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    public ErrorDetails error;
    public ValidationException(ErrorDetails error) {
        this.error = error;
    }
}
