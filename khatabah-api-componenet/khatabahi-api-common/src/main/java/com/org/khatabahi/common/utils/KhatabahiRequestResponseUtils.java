package com.org.khatabahi.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.common.base.response.GenericResponse;
import com.org.khatabahi.common.base.response.GenericResponseContext;
import com.org.khatabahi.common.constant.StatusCode;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Date;

public class KhatabahiRequestResponseUtils {
    private static final ObjectMapper mapper = InstanceUtils.getObjectMapperInstance();
    private static final String EMPTY_JSON_OBJECT_STRING = "{}";

    public KhatabahiRequestResponseUtils() {
    }
    public static String createResponse(String requestBody, String code, String message) throws IOException {
        return createResponseNode(requestBody, code, message, null).toString();
    }

    public static ObjectNode createResponseContext(String code, String message){
        return getResponseContext(code, message);
    }

    private static ObjectNode getResponseContext(String code, String message) {
        GenericResponseContext responseContext = new GenericResponseContext();
        responseContext.setStatusCode(code);
        responseContext.setStatusDescription(message);
        responseContext.setResponseDateTime(new Date().toString());
        return mapper.valueToTree(responseContext);
    }

    public static ObjectNode createResponseNode(String requestBody, String code, String message, String... traceId) throws IOException{
        ObjectNode requestNode = getObjectNode(requestBody);
        ObjectNode response = baseRequestConvertResponse(requestNode, code, message, traceId);
        JsonNode profile = requestNode.findValue("profile");
        if (profile != null){
            response.with("serviceResponseBody").set("profile", profile);
        }
        return response;
    }

    public static String convertRequestToResponse(String requestBody, StatusCode statusCode, boolean autoProfile){
        return convertRequestToResponse(requestBody, statusCode, false);
    }

    public static <T extends GenericRequest, R extends GenericResponse>R convertRequestToResponse(T request, StatusCode statusCode, Class<R> clazz) throws IOException{
        String requestStr = mapper.writeValueAsString(request);
        ObjectNode responseNode = createResponseNode(requestStr, statusCode.getStatusCode(), statusCode.getStatusDescription());
        return mapper.treeToValue(responseNode, clazz);
    }

    public static <R extends GenericResponse>String setResponseContext(R response, StatusCode statusCode) throws IOException{
        String responseStr = mapper.writeValueAsString(response);
        ObjectNode responseContext = createResponseContext(statusCode.getStatusCode(), statusCode.getStatusDescription());
        ObjectNode responseNode = mapper.readValue(responseStr, ObjectNode.class);
        if(!responseNode.has("serviceResponseBody")){
            responseNode.putPOJO("serviceResponseBody", mapper.createObjectNode());
        }
        responseNode.with("serviceResponseHeader").set("responseContext", responseContext);
        return responseNode.toString();
    }

    public static ObjectNode setResponseCodeAndDesc(ObjectNode responseNode, String responseCode, String responseDesc){
        responseNode.with("serviceResponseHeader").with("responseContext").put("statusCode", responseCode);
        responseNode.with("serviceResponseHeader").with("responseContext").put("statusDescription", responseCode);
        return responseNode;
    }

    private static ObjectNode baseRequestConvertResponse(ObjectNode requestNode, String code, String message, String[] traceId) {
        ObjectNode responseNode = mapper.createObjectNode();
        //set response context
        JsonNode requestContextNode = requestNode.findValue("requestContext");
        responseNode.with("serviceResponseHeader").set("responseContext", getResponseContext(code, message));
        responseNode.with("serviceResponseHeader").set("requestContext", requestContextNode);
        return responseNode;
    }

    private static ObjectNode getObjectNode(String requestBody) throws IOException {
        if(StringUtils.isEmpty(requestBody)){
            requestBody = EMPTY_JSON_OBJECT_STRING;
        }
        return  mapper.readValue(requestBody, ObjectNode.class);
    }
}
