package com.example.messageSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {
    @Autowired
    private Validator validator;
    private final MessageSourceUtils messageSourceUtils;

    @PostMapping("/validateFirstScreen")
    public ResponseEntity<VesselVoyageResponse> validateFirstScreen(@RequestHeader("flag") boolean flag) {

        CommonUtils.getHttpServletRequest().setAttribute(Constants.FLAG, "Imports");

        CommonUtils.getHttpServletRequest().setAttribute(Constants.MESSAGE_SOURCE, messageSourceUtils);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(validator.validation(flag));

    }

    @PostMapping("/validateSecondScreen")
    public ResponseEntity<VesselVoyageResponse> validateSecondScreen(@RequestHeader("flag") boolean flag) {

        CommonUtils.getHttpServletRequest().setAttribute(Constants.FLAG, "Adhoc");

        CommonUtils.getHttpServletRequest().setAttribute(Constants.MESSAGE_SOURCE, messageSourceUtils);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(validator.validation(flag));

    }
}
