package com.example.messageSource.validator;

import com.example.messageSource.config.CommonUtils;
import com.example.messageSource.utils.Constants;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class TicketValidator {

    public void validation(String ticketNumber) {

        if (!ticketNumber.isEmpty() && ticketNumber.length() > 9) {
            CommonUtils.addErrorToList(Constants.ERROR_CODE_2,
                    CommonUtils.getMessageSourceUtils().getProperty(Constants.ERROR_CODE_2));
        }

        // Check if the ticket number matches the pattern (starts with "ABC")
        if (!ticketNumber.isEmpty() && !Pattern.compile(Constants.PATTERN).matcher(ticketNumber).matches())
            CommonUtils.addErrorToList(Constants.ERROR_CODE_3,
                    CommonUtils.getMessageSourceUtils().getProperty(Constants.ERROR_CODE_3));
    }
}
