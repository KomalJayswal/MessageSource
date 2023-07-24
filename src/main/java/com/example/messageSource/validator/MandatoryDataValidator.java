package com.example.messageSource.validator;

import com.example.messageSource.config.CommonUtils;
import com.example.messageSource.utils.Constants;
import org.springframework.stereotype.Component;

@Component
public class MandatoryDataValidator {

    public void validation(String ticketNumber) {
        if (ticketNumber.isEmpty()) {
            CommonUtils.addErrorToList(Constants.ERROR_CODE_1,
                    CommonUtils.getMessageSourceUtils().getProperty(Constants.ERROR_CODE_1));
        }
    }
}
