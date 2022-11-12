package com.example.messageSource.config;

import com.example.messageSource.model.ErrorResponse;
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
     *            of the GCSS Error
     * @param errorMsg
     *            of the GCSS Error
     */
    public static void addOhmErrorToList(String errorCode, String errorMsg) {

        if (CollectionUtils.isEmpty(getOhmValidationErrors())) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(OhmError.builder().errorCode(errorCode).errorMessage(errorMsg).build());
            getHttpServletRequest().setAttribute(Constants.OHM_ERRORS, errors);
        } else {
            getOhmValidationErrors().add(OhmError.builder().errorCode(errorCode).errorMessage(errorMsg).build());
        }
    }
    /**
     * @return List of Ohm Errors set in the Servlet Request
     */
    public static List<OhmError> getOhmValidationErrors() {
        return (List<OhmError>) getHttpServletRequest().getAttribute(Constants.OHM_ERRORS);
    }
}