package com.example.messageSource.config;

import java.util.Locale;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class MessageSourceUtils {
    private static final Logger log = LoggerFactory.getLogger(MessageSourceUtils.class);
    private final MessageSource messageSource;

    public String getProperty(String key, Object... args) {
        try {
            HttpServletRequest httpRequest = ((ServletRequestAttributes)Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                    .getRequest();
            Locale locale = httpRequest.getLocale();
            return this.messageSource.getMessage(key, args, locale);
        } catch (Exception var5) {
            log.error("error occurred while fetching key {} from properties file", key);
            return this.getDefaultLocaleMessage(key, args);
        }
    }

    private String getDefaultLocaleMessage(String key, Object... args) {
        try {
            return this.messageSource.getMessage(key, args, Locale.getDefault());
        } catch (NoSuchMessageException var4) {
            log.error("errorMessage not found with key {}", key);
            return var4.getMessage();
        }
    }

    public MessageSourceUtils(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
