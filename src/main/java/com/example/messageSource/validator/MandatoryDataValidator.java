package com.example.messageSource.validator;

import com.example.messageSource.config.CommonUtils;
import com.example.messageSource.exception.ValidationException;
import com.example.messageSource.model.ErrorDetails;
import com.example.messageSource.utils.Constants;
import org.springframework.stereotype.Component;

@Component
public class MandatoryDataValidator {

    public void validation(String ticketNumber) {
        if (ticketNumber.isEmpty()) {
            throw new ValidationException(ErrorDetails.builder()
                    .errorCode(Constants.ERROR_CODE_1)
                    .errorMessage(CommonUtils.getMessageSourceUtils().getProperty(Constants.ERROR_CODE_1))
                    .build());
        }
    }
}
