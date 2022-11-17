package com.example.messageSource.model;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
public class ErrorResponse{
    public List<ErrorDetails> multipleErrors;

    public ErrorResponse(){

    }
    public ErrorResponse(List<ErrorDetails> multipleErrors) {
        this();
        this.multipleErrors = multipleErrors;
    }
}
