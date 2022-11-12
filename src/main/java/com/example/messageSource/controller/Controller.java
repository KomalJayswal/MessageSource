package com.example.messageSource.controller;

import com.example.messageSource.config.CommonUtils;
import com.example.messageSource.config.MessageSourceUtils;
import com.example.messageSource.model.ErrorResponse;
import com.example.messageSource.model.SuccessResponse;
import com.example.messageSource.utils.Constants;
import com.example.messageSource.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {
    @Autowired
    private Validator validator;
    private final MessageSourceUtils messageSourceUtils;

    @PostMapping("/validateFirstScreen")
    public ResponseEntity<SuccessResponse> validateFirstScreen(@RequestHeader("flag") boolean flag) {

        CommonUtils.getHttpServletRequest().setAttribute(Constants.FLAG, "Imports");

        CommonUtils.getHttpServletRequest().setAttribute(Constants.MESSAGE_SOURCE, messageSourceUtils);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(validator.validation(flag));

    }

    @PostMapping("/validateSecondScreen")
    public ResponseEntity<SuccessResponse> validateSecondScreen(@RequestHeader("flag") boolean flag) {

        CommonUtils.getHttpServletRequest().setAttribute(Constants.FLAG, "Adhoc");

        CommonUtils.getHttpServletRequest().setAttribute(Constants.MESSAGE_SOURCE, messageSourceUtils);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(validator.validation(flag));

    }
}
