/*
package com.org.khatabahi.api.base.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import com.org.khatabahi.api.base.services.FinastraResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component("finastraResponseHandler")
public class FinastraResponseHandlerImpl implements FinastraResponseHandler {

    private static final String JSON_PATH_TO_RESPONSE_CODE = "$.serviceResponseHeader.responseContext.responseCode";
    private static final String JSON_PATH_TO_RESPONSE_DESC = "$.serviceResponseHeader.responseContext.responseDescription";
    @Autowired
    private ResponseCodeMappings responseCodeMappings;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public GenericResponse generateResponse(String prefix, String requestBody, String finastraResponse) throws JSONException, IOException {
        GenericResponse response = new GenericResponse();
        GenericRequest gebRequest = parseGenericRequest(requestBody);

        GenericResponseHeader resHdr = new GenericResponseHeader();
        response.setServiceResponseHeader(resHdr);
        GenericResponseContext responseContext = generateResponseContext(prefix, finastraResponse);
        resHdr.setResponseContext(responseContext);
        resHdr.setRequesterContext(gebRequest.getServiceRequestHeader().getRequesterContext());


        GenericResponseBody resBody = new GenericResponseBody();
        response.setServiceResponseBody(resBody);
        resBody.setProfile(gebRequest.getServiceRequestBody().getProfile());

        return response;
    }

    @Override
    public String generateStringResponse(String prefix, String finastraResponse) {
        DocumentContext documentContext = JsonPath.parse(finastraResponse);
        String finastraCode = documentContext.read(JSON_PATH_TO_RESPONSE_CODE, String.class);
        String finastraDescription = documentContext.read(JSON_PATH_TO_RESPONSE_DESC, String.class);

        String gebResCode = convertToGEBCode(prefix, finastraCode);
        String gebResDesc = String.format(responseCodeMappings.getFinDescTemplate(), gebResCode, finastraDescription);
        documentContext.set(JSON_PATH_TO_RESPONSE_CODE, gebResCode);
        String gebResponse = documentContext.set(JSON_PATH_TO_RESPONSE_DESC, gebResDesc).jsonString();

        return gebResponse;
    }

    private GenericRequest parseGenericRequest(String requestBody) {
        return GEBNGJsonUtils.parseJSONString(requestBody, GenericRequest.class);
    }

    private GenericResponseContext generateResponseContext(String prefix, String finRspns) throws JSONException, IOException {
        JSONObject finRspnsJSON = new JSONObject(finRspns);

        GenericResponseContext responseContext = getFinastraGenericResponseContext(finRspnsJSON);
        String gebRpnsCode = convertToGEBCode(prefix, responseContext.getStatusCode());
        responseContext.setStatusCode(gebRpnsCode);
        //TODO load the template "{code} - {message}" here from responsecode.properties
        String finResDesc = String.format(responseCodeMappings.getFinDescTemplate(),
                responseContext.getStatusCode(),
                responseContext.getStatusDescription());
        responseContext.setStatusDescription(finResDesc);
        return responseContext;
    }


    private String convertToGEBCode(String prefix, String finastraRspnsCode) {
        String gebRspnCode = (GEBNGValidator.isBlank(responseCodeMappings.getGEBResponseCode(prefix, finastraRspnsCode)) ? finastraRspnsCode
                : responseCodeMappings.getGEBResponseCode(prefix, finastraRspnsCode));
        return gebRspnCode;
    }

    private GenericResponseContext getFinastraGenericResponseContext(JSONObject jsonObj) throws IOException, JSONException {
        String srcResponseDesc = "";
        String srcResponseCode = "";
        if (jsonObj.has(Constants.PAYLOAD_ERROR_MESSAGE)) {
            srcResponseCode = String.valueOf(jsonObj.get(Constants.PAYLOAD_ERROR_CODE));
            srcResponseDesc = jsonObj.getString(Constants.PAYLOAD_ERROR_MESSAGE);
        } else if (jsonObj.has(Constants.PAYLOAD_RESPONSE)) {
            ServiceResponseHeader resHdr = GEBNGJsonUtils.parseJSONString(
                    (jsonObj.getJSONObject(Constants.PAYLOAD_RESPONSE)).toString(), ServiceResponseHeader.class);
            srcResponseCode = resHdr.getResponseContext().getStatusCode();
            srcResponseDesc = resHdr.getResponseContext().getStatusDescription();
            if(GEBNGValidator.isBlank(resHdr.getResponseContext().getStatusCode())) {
                JSONObject statusObj = jsonObj.getJSONObject(Constants.PAYLOAD_RESPONSE)
                        .getJSONObject(Constants.PAYLOAD_RESPONSE_CONTEXT);
                srcResponseCode = statusObj.getString(Constants.PAYLOAD_RESPONSE_CODE);
                srcResponseDesc = statusObj.getString(Constants.PAYLOAD_RESPONSE_MESSAGE);
            }
        }
        GenericResponseContext resCtx = new GenericResponseContext();
        resCtx.setStatusCode(srcResponseCode);
        resCtx.setStatusDescription(srcResponseDesc);
        resCtx.setResponseDateTime(GEBNGDateUtils.getCurrentTimeStamp());
        return resCtx;
    }

    public void setResponseCodeMappings(ResponseCodeMappings responseCodeMappings) {
        this.responseCodeMappings = responseCodeMappings;
    }
}
*/
