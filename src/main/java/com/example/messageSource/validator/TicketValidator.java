package com.example.messageSource.validator;

import com.example.messageSource.config.CommonUtils;
import com.example.messageSource.exception.ValidationException;
import com.example.messageSource.model.ErrorDetails;
import com.example.messageSource.utils.Constants;
import org.springframework.stereotype.Component;

@Component
public class TicketValidator {

    public void validation(String ticketNumber) {

        if (ticketNumber.length()<9) {
            throw new ValidationException(ErrorDetails.builder()
                    .errorCode(Constants.ERROR_CODE_2)
                    .errorMessage(CommonUtils.getMessageSourceUtils().getProperty(Constants.ERROR_CODE_2))
                    .build());
        }
    }
}
