package com.example.messageSource;
import org.springframework.stereotype.Component;

@Component
public class Validator  {

    public VesselVoyageResponse validation(boolean flag) {

        if (flag) {
            return VesselVoyageResponse.builder()
                    .errorCode(VesselVoyageErrorConstants.PORT_PROFILE_NOT_FOUND)
                    .errorMessage(CommonUtils.getMessageSourceUtils().getProperty(VesselVoyageErrorConstants.PORT_PROFILE_NOT_FOUND)).build();
        }
        return null;
    }
}
