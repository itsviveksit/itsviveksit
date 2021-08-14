package com.org.khatabahi.core.builder;

import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.common.base.request.GenericRequestBody;
import com.org.khatabahi.common.base.request.GenericRequestHeader;
import com.org.khatabahi.common.base.response.GenericResponse;
import com.org.khatabahi.common.base.response.GenericResponseBody;
import com.org.khatabahi.common.base.response.GenericResponseContext;
import com.org.khatabahi.common.base.response.GenericResponseHeader;
import com.org.khatabahi.common.constant.StatusCode;
import com.org.khatabahi.logger.KhatabahiLogger;

import java.util.Date;
import java.util.function.Function;

public class CommonResponseBuilder {

    private static final KhatabahiLogger log = KhatabahiLogger.getLogger(CommonResponseBuilder.class);

    private CommonResponseBuilder(){
    }

    public static <T extends GenericRequestBody, X extends GenericResponseBody> GenericResponse<GenericResponseHeader, X> build(GenericRequest<GenericRequestHeader, T> request, String statusCode, String statusDesc, Function<T, X> responseBodyEnhancer){
        log.info("Build service response.");
        log.info("statusCode :", statusCode);
        log.info("statusDesc :", statusDesc);
        GenericResponseContext responseContext = GenericResponseContext.builder()
                .statusCode(statusCode)
                .statusDescription(statusDesc)
                .responseDateTime(new Date().toString())
                .build();
        GenericResponse.GenericResponseBuilder<GenericResponseHeader, X> responseBuilder = GenericResponse.builder();
        responseBuilder.serviceResponseHeader(
                GenericResponseHeader.builder().requesterContext(request.getServiceRequestHeader()
                .getRequesterContext())
                .responseContext(responseContext)
                .build()
        );
        if (responseBodyEnhancer != null){
            responseBuilder.serviceResponseBody(responseBodyEnhancer.apply(request.getServiceRequestBody()));
        }
        return responseBuilder.build();
    }

    //Builds the response Used StatusCode enum to
    public static <T extends GenericRequestBody, X extends GenericResponseBody> GenericResponse<GenericResponseHeader, X> build(GenericRequest<GenericRequestHeader, T> request, StatusCode statusCode, StatusCode statusDesc, Function<T, X> responseBodyEnhancer){
        return build(request, statusCode.getStatusCode(), statusDesc.getStatusDescription(), responseBodyEnhancer);
    }

}
