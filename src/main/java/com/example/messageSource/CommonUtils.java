package com.example.messageSource;

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

}