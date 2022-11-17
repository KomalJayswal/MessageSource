# MessageSource

## Getting Started

### Problem Statement

Create a microservice to throw a validation message from properties file 
as per the endpoint called.

### Curl Commands

```
curl --location --request POST 'http://localhost:8080/validateFirstScreen' \
--header 'Content-Type: application/json' \
--header 'flag: true' \
--header 'Cookie: JSESSIONID=5465B0BB022F81E4DAAADA5599DD0589' \
--data-raw ''
```
<I>Success Response</I>
```json
{
  "message": "SUCCESS"
}
```

</details>

<I>Error Response</I>
```json
{
    "multipleErrors": [
        {
            "errorCode": "101",
            "errorMessage": "First Screen Error Message for First Validation Check"
        },
        {
            "errorCode": "102",
            "errorMessage": "First Screen Error Message for Second Validation Check"
        }
    ]
}
```

</details>

```
curl --location --request POST 'http://localhost:8080/validateSecondScreen' \
--header 'Content-Type: application/json' \
--header 'flag: true' \
--header 'Cookie: JSESSIONID=5465B0BB022F81E4DAAADA5599DD0589' \
--data-raw ''
```

### Solution

1. Install and set up Java, Maven and Postman in your system.
2. Build a fresh springboot Application using [Spring Initializer](https://start.spring.io).

</details>
<details>
    <summary><I>Select the following to generate the application. </I></summary>

<u>Project</u> : Maven Project <br>
<u>Language</u> : Java <br>
<u>Spring Boot</u> : 2.7.5 <br>
<u>Group</u> : net.learning <br>
<u>Artifact</u> : messageSource <br>
<u>Name</u> : messageSource <br>
<u>Package name</u> : net.learning.messageSource <br>
<u>Packaging</u> : JAR <br>
<u>Java</u> : 17 <br>
Then, add spring web dependency : Lombok , Spring Web <br><br>

</details>

3. Create a `config` package. Under the package, create `MessageSourceUtils` Java Class.
```java
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
```
4. Create `Constants` Java Class under `utils` package.
```java
public class Constants {

    public static final String MESSAGE_SOURCE = "MESSAGE_SOURCE";
    public static final String ERRORS = "ERRORS";
    public static final String FLAG = "FLAG";
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR_CODE_1 = "101";
    public static final String ERROR_CODE_2 = "102";
}
```
5. Create `CommonUtils` Java class under `config` package.
```java
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
```
6. Create `AppConfig` Java Class, under `config` package,
```java
import com.example.messageSource.utils.Constants;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:messages_adhoc.properties")
public class AppConfig {

    @Bean
    @Primary
    public static MessageSourceUtils messageSourceUtils(MessageSource messageSource, Environment environment){
        return new MessageSourceUtils(messageSource){
            @Override
            public String getProperty(String key, Object... args) {
                String isAdhocFlow = CommonUtils.getHttpServletRequest().getAttribute(Constants.FLAG).toString();
                if(isAdhocFlow.equals("Adhoc")){
                    String message = environment.getProperty(key);
                    if(null != message && !message.isEmpty()){
                        for(int i=0;i < args.length ; i++){
                            message = message.replace("{" + i + "}",String.valueOf(args[i]));
                        }
                    }
                    return message;
                }
                return super.getProperty(key, args);
            }
        };
    }
}
```
7. Create `model` package and under the package create `ErrorDetails` Java Class
```java
import lombok.*;

@Builder
@Getter
@Setter
public class ErrorDetails {

    private String errorCode;
    private String errorMessage;
}
```
8. Create `model` package and under the package create `ErrorResponse` Java Class
```java
import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
public class ErrorResponse{
    public List<ErrorDetails> multipleErrors;

    public ErrorResponse(){

    }
    public ErrorResponse(List<ErrorDetails> multipleErrors) {
        this();
        this.multipleErrors = multipleErrors;
    }
}
```
9. Create `model` package and under the package create `SuccessResponse` Java Class
```java
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse {

    private String message;
}
```
10. Create `validator` package and then create a `Validator1` Java Class under it.
```java
import com.example.messageSource.config.CommonUtils;
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
```
11. Create `validator` package and then create a `Validator2` Java Class under it.
```java
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
```
12. Create `exception` package and then create a `ValidationException` Java Class under it.
```java
import com.example.messageSource.model.ErrorDetails;
import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends RuntimeException {

    public List<ErrorDetails> multipleErrors;
    public ValidationException(List<ErrorDetails> errors) {
        this.multipleErrors = errors;
    }
}
```
13. Create `exception` package and then create a `ErrorHandler` Java Class under it.
```java
import com.example.messageSource.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

@RestControllerAdvice
public class ErrorHandler{

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException validationException,
                                                                   ServletWebRequest servletWebRequest) {
        ErrorResponse errorResponse =  new ErrorResponse(validationException.getMultipleErrors());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
```
14. Create `controller` package. Under the package create `Controller` Java Class.
```java
import com.example.messageSource.config.CommonUtils;
import com.example.messageSource.config.MessageSourceUtils;
import com.example.messageSource.model.SuccessResponse;
import com.example.messageSource.service.Service;
import com.example.messageSource.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller {
    @Autowired
    private Service service;
    private final MessageSourceUtils messageSourceUtils;

    @PostMapping("/validateFirstScreen")
    public ResponseEntity<SuccessResponse> validateFirstScreen(@RequestHeader("flag") boolean flag) {

        CommonUtils.getHttpServletRequest().setAttribute(Constants.FLAG, "Imports");

        CommonUtils.getHttpServletRequest().setAttribute(Constants.MESSAGE_SOURCE, messageSourceUtils);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.conditionalCheck(flag));
    }

    @PostMapping("/validateSecondScreen")
    public ResponseEntity<SuccessResponse> validateSecondScreen(@RequestHeader("flag") boolean flag) {

        CommonUtils.getHttpServletRequest().setAttribute(Constants.FLAG, "Adhoc");

        CommonUtils.getHttpServletRequest().setAttribute(Constants.MESSAGE_SOURCE, messageSourceUtils);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.conditionalCheck(flag));

    }
}
```
15. Create `application.yml` to enable the overriding bean.
```yaml
server.port : 8080

spring.main.allow-bean-definition-overriding : true
```
16. Create 2 property files. <br>
a. <u>messages.properties</u>
```properties
190001002=First Screen Error Message
```
b. <u>messages_adhoc.properties</u>
```properties
190001002=Second Screen Error Message
```
17. Run the application. 
18. Open Postman in your system. Import the curl command provided above and hit the endpoints.

### Improvements

* Craete a use case and add descriptions
* Add java comments
* Add standard variable and method names
* Segreated in standard packages

### Read on
* spring.main.allow-bean-definition-overriding
* MessageSourceUtils
