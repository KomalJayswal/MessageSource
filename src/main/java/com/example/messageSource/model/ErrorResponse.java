package com.example.messageSource.model;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse{
    public List<ErrorDetails> errors;
}
