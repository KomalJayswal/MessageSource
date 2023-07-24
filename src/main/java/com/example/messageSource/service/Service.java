package com.example.messageSource.service;

import com.example.messageSource.config.CommonUtils;
import com.example.messageSource.utils.Constants;
import com.example.messageSource.validator.MandatoryDataValidator;
import com.example.messageSource.validator.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Service {

    @Autowired
    private MandatoryDataValidator mandatoryDataValidator;

    @Autowired
    private TicketValidator ticketValidator;

    public String conditionalCheck(String ticketNumber){

        mandatoryDataValidator.validation(ticketNumber);
        ticketValidator.validation(ticketNumber);
        CommonUtils.validateErrors();
        return Constants.SUCCESS+ticketNumber;
    }
}
