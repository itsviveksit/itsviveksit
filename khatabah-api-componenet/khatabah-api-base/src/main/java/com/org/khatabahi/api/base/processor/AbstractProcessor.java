

package com.org.khatabahi.api.base.processor;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.khatabahi.common.utils.InstanceUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import java.io.IOException;
import java.util.Map;

/**
 * @author vivek
 * @version 1.0
 * @date 02/07/2019
 */
public abstract class AbstractProcessor implements Processor{

    protected final ObjectMapper mapper = InstanceUtils.getObjectMapperInstance();
/*    @Autowired
    private JsonPathProvider jsonPathProvider;*/

    /**
     *
     * @param exchange
     * @description provide abstract processor  and utils for common usage
     * @author tmpil9
     * @date 02/07/2019 1:45 PM
     */

    @Override
    public abstract void process(Exchange exchange) throws IOException;

    public String getRouteId(Exchange exch) {
        return exch.getUnitOfWork().getRouteContext().getRoute().getId();
    }

    protected Message getOriginalMessage(Exchange exchange){
        return  exchange.getUnitOfWork().getOriginalInMessage();
    }

    public String getBodyAsaString(Exchange exchange){
        try{
            return mapper.writeValueAsString(exchange.getIn().getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     * Get ErrorCode description
     *
     * @param exch
     * @param errorCode
     * @return
     */
    public String getErrorMessage(String errorCode, Exchange exch) {
        //TODO
        return "";
    }

    /**
     * Get countrycode from Exchange
     *
     * @param exch
     * @return
     */
    public String getCountryCode(Exchange exch) {
        //TODO
        return "";
    }

    /**
     * Get MessageContentsList or response or request from exch Body
     *
     * @param exch
     * @return object
     */
    public Object getBody(Exchange exch) {
        return exch.getIn().getBody();
    }

    /**
     * Get MessageContentsList or response or request from exch Body
     *
     * @param exch
     * @return object
     */
    public Object getBody(Exchange exch, Class<?> obj) {
        return exch.getIn().getBody(obj);
    }

    /**
     * Set the input/response/contentslist in exch body
     *
     * @param exch
     * @param input
     * @return
     */
    public void setBody(Exchange exch, Object input) {
        exch.getOut().setBody(input);
    }

    public void setHeader(Exchange exch, String key, Object value) {
        exch.getIn().getHeaders().put(key, value);
    }

    public Map<String, Object> getHeaders(Exchange exch) {
        return exch.getIn().getHeaders();
    }
    public Object getHeader(Exchange exch, String key) {
        return exch.getIn().getHeader(key);
    }
    public <T>T getHeader(Exchange exch, String key, Class<T> clazz) {
        return exch.getIn().getHeader(key, clazz);
    }

    /**
     *
     * @param  jsonStr rule
     * @return String
     * @author tmpil9
     * @date 14/07/2019 10:31 PM
     * try to find result according the rule, otherwise throw DefaultAPI exception with error code
     */
   /* @Override
    public String jsonPathChecker(String jsonStr, String rule) {
        return jsonPathChecker(jsonStr, rule, null);
    }*/
/*
    *//**
     *
     * @param  jsonStr rule statusCodeEnum
     * @return String
     * @author tmpil9
     * @date 14/07/2019 10:31 PM
     * try to find result according the rule, otherwise throw DefaultAPI exception with error code
     *//*
    @Override
    public String jsonPathChecker(String jsonStr, String rule, StatusCode statusCodeEnum) {
        String statusCode = jsonPathFinder(jsonStr, JsonPathProvider.RESPONSE_CODE_RULE);
        String statusCodeDesc = jsonPathFinder(jsonStr, JsonPathProvider.RESPONSE_DESC_RULE);
        if (statusCodeEnum == null) {
            statusCodeEnum = StatusCode.GENERIC_FAILURE_PMT_199;
        }
        if (StringUtils.isEmpty(statusCode)) {
            statusCode = statusCodeEnum.getStatusCode();
        }
        if (StringUtils.isEmpty(statusCodeDesc)) {
            statusCodeDesc = statusCodeEnum.getStatusDescription();
        }
        List<String> judgeList = JsonPath.parse(jsonStr).read(rule);
        final DefaultApiException apiException = new DefaultApiException(statusCode, statusCodeDesc);
        return Optional.ofNullable(judgeList).orElse(Collections.emptyList())
                .stream().findFirst().orElseThrow(() -> apiException);
    }

    *//**
     *
     * @param jsonStr rule
     * @return String
     * @author tmpil9
     * @date 14/07/2019 10:29 PM
     * find the error code whether exist
     *//*
    @Override
    public String jsonPathFinder(String jsonStr, String rule) {
        List<String> judgeList = JsonPath.parse(jsonStr).read(rule);
        return Optional.ofNullable(judgeList).orElse(Collections.emptyList())
                .stream().findFirst().orElse("");
    }

    @Override
    public String jsonPathPutter(String jsonStr, String rule, String key, String value) {
        DocumentContext newValue = JsonPath.parse(jsonStr).put(rule, key, value);
        return newValue.jsonString();
    }*/
}
