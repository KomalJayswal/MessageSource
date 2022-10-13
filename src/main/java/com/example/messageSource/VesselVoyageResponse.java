package com.example.messageSource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VesselVoyageResponse {

    private String errorCode;

    private String errorMessage;

}
