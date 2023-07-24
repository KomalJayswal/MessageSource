package com.example.messageSource.config;

import com.example.messageSource.utils.Constants;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:messages_bus.properties")
public class AppConfig {

    @Bean
    @Primary
    public static MessageSourceUtils messageSourceUtils(MessageSource messageSource, Environment environment){
        return new MessageSourceUtils(messageSource){
            @Override
            public String getProperty(String key, Object... args) {
                String isBusTicket = CommonUtils.getHttpServletRequest().getAttribute(Constants.FLAG).toString();
                if(isBusTicket.equals(Constants.BUS)){
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
