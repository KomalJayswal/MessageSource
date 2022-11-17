package com.example.messageSource.validator;

import com.example.messageSource.config.CommonUtils;
import com.example.messageSource.utils.Constants;
import org.springframework.stereotype.Component;

@Component
public class Validator2 {

    public void validation(boolean flag) {

        if (flag) {
            CommonUtils.addOhmErrorToList(Constants.ERROR_CODE_2,
                    CommonUtils.getMessageSourceUtils().getProperty(Constants.ERROR_CODE_2));
        }
    }
}
