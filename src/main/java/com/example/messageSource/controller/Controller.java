package com.example.messageSource.controller;

import com.example.messageSource.config.MessageSourceUtils;
import com.example.messageSource.service.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.messageSource.config.CommonUtils;
import com.example.messageSource.utils.Constants;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {
    @Autowired
    private Service service;
    private final MessageSourceUtils messageSourceUtils;

    @PostMapping("/validateBusTicket")
    public ResponseEntity<String> validateBusTicket(@RequestHeader("ticketNumber") String ticketNumber) {

        CommonUtils.getHttpServletRequest().setAttribute(Constants.TRANSPORTATION_MODE, Constants.BUS);
        CommonUtils.getHttpServletRequest().setAttribute(Constants.MESSAGE_SOURCE, messageSourceUtils);

        return ResponseEntity.status(HttpStatus.OK).body(service.conditionalCheck(ticketNumber));
    }

    @PostMapping("/validateRailwayTicket")
    public ResponseEntity<String> validateRailwayTicket(@RequestHeader("ticketNumber") String ticketNumber) {

        CommonUtils.getHttpServletRequest().setAttribute(Constants.TRANSPORTATION_MODE, Constants.RAILWAY);
        CommonUtils.getHttpServletRequest().setAttribute(Constants.MESSAGE_SOURCE, messageSourceUtils);

        return ResponseEntity.status(HttpStatus.OK).body(service.conditionalCheck(ticketNumber));

    }
}
