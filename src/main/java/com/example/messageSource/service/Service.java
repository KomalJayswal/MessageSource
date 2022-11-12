package com.example.messageSource.service;

import com.example.messageSource.config.CommonUtils;
import com.example.messageSource.model.SuccessResponse;
import com.example.messageSource.utils.Constants;
import com.example.messageSource.validator.Validator1;
import com.example.messageSource.validator.Validator2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Service {

    @Autowired
    private Validator1 validator1;

    @Autowired
    private Validator2 validator2;

    public SuccessResponse conditionalCheck(boolean flag){

        validator1.validation(flag);
        validator2.validation(flag);

        CommonUtils.validateErrors();

        return SuccessResponse.builder().message(Constants.SUCCESS).build();
    }
}
