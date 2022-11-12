package com.example.messageSource.config;

import com.example.messageSource.exception.ValidationException;
import com.example.messageSource.model.ErrorDetails;
import com.example.messageSource.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CommonUtils {

    private CommonUtils() {
        // No-OP
    }

    /**
     * Get current HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
    }

    /**
     * Get MessageSourceUtils from current Request Attribute
     *
     * @return MessageSourceUtils
     */
    public static MessageSourceUtils getMessageSourceUtils() {
        return ((MessageSourceUtils) getHttpServletRequest().getAttribute(Constants.MESSAGE_SOURCE));
    }
    /**
     * Adds Error Validations if found
     *
     * @param errorCode
     *            of the Error
     * @param errorMsg
     *            of the Error
     */
    public static void addOhmErrorToList(String errorCode, String errorMsg) {

        if (CollectionUtils.isEmpty(getValidationErrors())) {
            List<ErrorDetails> errors = new ArrayList<>();
            errors.add(ErrorDetails.builder().errorCode(errorCode).errorMessage(errorMsg).build());
            getHttpServletRequest().setAttribute(Constants.ERRORS, errors);
        } else {
            getValidationErrors().add(ErrorDetails.builder().errorCode(errorCode).errorMessage(errorMsg).build());
        }
    }
    /**
     * @return List of Errors set in the Servlet Request
     */
    public static List<ErrorDetails> getValidationErrors() {
        return (List<ErrorDetails>) getHttpServletRequest().getAttribute(Constants.ERRORS);
    }

    /**
     * Throws Validations Exception if any Error is present in the Servelet ERRORS List
     */
    public static void validateErrors() {

        if (Objects.nonNull(getValidationErrors())) {
            throw new ValidationException(getValidationErrors());
        }
    }
}