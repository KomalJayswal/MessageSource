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
    public static final String FLAG = "FLAG";
    public static final String ERROR_CODE = "190001002";
}
```
5. Create `CommonUtils` Java class under `config` package.
```java
import net.learning.messageSource.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
```
6. Create `AppConfig` Java Class, under `config` package,
```java
import net.learning.messageSource.utils.Constants;
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
7. Create `model` package and under the package create `Response` Java Class
```java
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private String errorCode;

    private String errorMessage;
}
```
8. Create `validator` package and then create a `Validator` Java Class under it.
```java
import net.learning.messageSource.config.CommonUtils;
import net.learning.messageSource.model.Response;
import net.learning.messageSource.utils.Constants;
import org.springframework.stereotype.Component;

@Component
public class Validator  {

    public Response validation(boolean flag) {

        if (flag) {
            return Response.builder()
                    .errorCode(Constants.ERROR_CODE)
                    .errorMessage(CommonUtils.getMessageSourceUtils().getProperty(Constants.ERROR_CODE)).build();
        }
        return null;
    }
}
```
9. Create `controller` package. Under the package create `Controller` Java Class.
```java
import net.learning.messageSource.config.CommonUtils;
import net.learning.messageSource.config.MessageSourceUtils;
import net.learning.messageSource.model.Response;
import net.learning.messageSource.utils.Constants;
import net.learning.messageSource.validator.Validator;
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
    private Validator validator;
    private final MessageSourceUtils messageSourceUtils;

    @PostMapping("/validateFirstScreen")
    public ResponseEntity<Response> validateFirstScreen(@RequestHeader("flag") boolean flag) {

        CommonUtils.getHttpServletRequest().setAttribute(Constants.FLAG, "Imports");

        CommonUtils.getHttpServletRequest().setAttribute(Constants.MESSAGE_SOURCE, messageSourceUtils);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(validator.validation(flag));

    }

    @PostMapping("/validateSecondScreen")
    public ResponseEntity<Response> validateSecondScreen(@RequestHeader("flag") boolean flag) {

        CommonUtils.getHttpServletRequest().setAttribute(Constants.FLAG, "Adhoc");

        CommonUtils.getHttpServletRequest().setAttribute(Constants.MESSAGE_SOURCE, messageSourceUtils);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(validator.validation(flag));

    }
}
```
10. Create 2 property files. <br>
a. <u>messages.properties</u>
```properties
190001002=First Screen Error Message
```
b. <u>messages_adhoc.properties</u>
```properties
190001002=Second Screen Error Message
```
11. Run the application. 
12. Open Postman in your system. Import the curl command provided above and hit the endpoints.

### Improvements

* Craete a use case and add descriptions
* Add java comments
* Add standard variable and method names
* Segreated in standard packages

### Read on
* spring.main.allow-bean-definition-overriding
* MessageSourceUtils
