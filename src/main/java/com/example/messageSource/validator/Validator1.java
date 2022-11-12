package com.example.messageSource.validator;

import com.example.messageSource.config.CommonUtils;
import com.example.messageSource.model.SuccessResponse;
import com.example.messageSource.utils.Constants;
import org.springframework.stereotype.Component;

@Component
public class Validator1 {

    public void validation(boolean flag) {

        if (flag) {
            CommonUtils.addOhmErrorToList(Constants.ERROR_CODE_1,
                    CommonUtils.getMessageSourceUtils().getProperty(Constants.ERROR_CODE_1));
        }
    }
}
