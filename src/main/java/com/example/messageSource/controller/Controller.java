package com.example.messageSource.controller;

import com.example.messageSource.config.MessageSourceUtils;
import com.example.messageSource.service.Service;
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
    private Service service;
    private final MessageSourceUtils messageSourceUtils;

    @PostMapping("/validateBusTicket")
    public ResponseEntity<String> validateBusTicket(@RequestHeader("ticketNumber") String ticketNumber) {

        return ResponseEntity.status(HttpStatus.OK).body(service.conditionalCheck(ticketNumber));
    }

    @PostMapping("/validateRailwayTicket")
    public ResponseEntity<String> validateRailwayTicket(@RequestHeader("ticketNumber") String ticketNumber) {

        return ResponseEntity.status(HttpStatus.OK).body(service.conditionalCheck(ticketNumber));

    }
}
