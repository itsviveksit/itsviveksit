/*
 * System Name         : GEBNexGen
 * Program Id          : uob-gebng
 * Author              : tmpil9
 * Date                : 08/07/2019
 * Copyright (c) United Overseas Bank Limited Co.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * United Overseas Bank Limited Co. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * United Overseas Bank Limited Co.
 */

package com.org.khatabahi.api.base.processor;

import com.fasterxml.jackson.core.type.TypeReference;

import com.org.khatabahi.api.base.validation.GEBValidationService;
import com.org.khatabahi.common.exception.DefaultApiException;
import com.org.khatabahi.common.utils.InstanceUtils;
import com.org.khatabahi.logger.KhatabahiLogger;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.converter.stream.InputStreamCache;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class SimpleProcessor<T, C> extends AbstractProcessor {
    private static final KhatabahiLogger LOGGER = KhatabahiLogger.getLogger(SimpleProcessor.class);
  /*  @Autowired
    private TemplateEngineConvertFactory engineConvertFactory;
    @Autowired
    protected CommonConfigProperties configProperties;*/
    @Autowired
    protected ProducerTemplate producerTemplate;

    @Autowired
    private GEBValidationService<T> gebValidationService;

    private void initialize(Exchange exchange) throws IOException {
        Class<T> clazz = getLatestGenericSuperClass(this.getClass());
        if (exchange.getIn().getBody() instanceof InputStreamCache) {
            if (!clazz.equals(String.class)) {
                String payload = exchange.getIn().getBody(String.class);
                T convertedBody = InstanceUtils.getObjectMapperInstance().readValue(payload, clazz);
                exchange.getIn().setBody(convertedBody);
            }
        }
    }
    protected Class<T> getLatestGenericSuperClass(Class<? extends SimpleProcessor> clazz){
        if(clazz.getGenericSuperclass() instanceof ParameterizedType){
            Type[] params = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
            return ((Class) params[0]);
        }else {
            return getLatestGenericSuperClass((Class<? extends SimpleProcessor>)clazz.getGenericSuperclass());
        }
    }

    public void preProcess(Exchange exchange) throws IOException{

    }

    public void validation(Exchange exchange) throws IOException {
        Class<T> clazz = getLatestGenericSuperClass(this.getClass());
        T payload = exchange.getIn().getBody(clazz);
        gebValidationService.validatePayload(this.getClass(), payload);
    }

   /* public String getToken(Exchange exchange) {
        T body = getExchangeBody(exchange);
        return producerTemplate.requestBodyAndHeaders(URIConstants.URI_CCS_SECURED_CSF_TOKEN.getUri(), body, getHeaders(exchange), String.class);
    }*/

    /**
     *
     * @param exchange
     * @return T
     * @author tmpil9
     * @date 12/07/2019 8:49 PM
     * @description
     */
    public T getExchangeBody(Exchange exchange) throws IOException {
        try {
            Class<T> clazz = getLatestGenericSuperClass(this.getClass());
            if (exchange.getIn().getBody() instanceof Map && clazz.equals(String.class)) {
                return (T) InstanceUtils.getObjectMapperInstance().writeValueAsString(exchange.getIn().getBody(Map.class));
            } else if (exchange.getIn().getBody() instanceof Map) {
                String jsonStr = InstanceUtils.getObjectMapperInstance().writeValueAsString(exchange.getIn().getBody(Map.class));
                return InstanceUtils.getObjectMapperInstance().readValue(jsonStr, new TypeReference<T>() {});
            }
            return exchange.getIn().getBody(clazz);
        } catch (IOException e) {
            throw new DefaultApiException("generic type covert error", "not able to convert ", e);
        }
    }

   /* public String templateConvert(Exchange exchange, Object target, String templateName) {
        String templatePath = configProperties.getTemplates().get(templateName);
        if (StringUtils.isEmpty(templateName)) {
            throw new DefaultApiException(StatusCode.NO_RECORD_FOUND.getStatusCode(), StatusCode.NO_RECORD_FOUND.getStatusDescription());
        }
        return templateEngineConvert(target, templatePath);
    }
*/
    public abstract C execute(Exchange exchange) throws IOException;

    @Override
    public void process(Exchange exchange) throws IOException {
        initialize(exchange);

        /** STEP1 execute validate body */
        validation(exchange);

        /** STEP2 enrich body or header */
        preProcess(exchange);

        /** STEP3 check token action */
       /* if (enableGetToken()) {
            getToken(exchange);
        }*/
        /** STEP4 execute process */
        LOGGER.info("Start of execute process:", this.getClass().getSimpleName());
        C result = execute(exchange);
        LOGGER.info("end of execute process:", this.getClass().getSimpleName());

        /** STEP5 enrich body or header */
        postProcess(exchange, getExchangeBody(exchange), result);
        /** STEP6 enrich body or header */
        LOGGER.info("end of execute class:", this.getClass().getSimpleName());
    }

    public void postProcess(Exchange exchange, T request, C result) throws IOException {
        /**
         * Rest Binding Mode 'RestBindingMode.json' makes the JSON text received from client if is
         * @see String it is with double quotes
         *
         *  this reason is because the OutType will call jackson, convert String to json
         */
        if (result instanceof String) {
            exchange.getIn().setBody(result);
        } else {
            exchange.getIn().setBody(InstanceUtils.getObjectMapperInstance().writeValueAsString(result));
        }
    }

    public boolean enableGetToken() {
        return false;
    }


    /**
     *
     * @param target templatePath
     * @return String
     * @author tmpil9
     * @date 14/07/2019 5:50 PM
     * @description According to the templatePath suffix to switch engine and generate Template.
     */
   /* private String templateEngineConvert(Object target, String templatePath) {
        TemplateEngineConvert templateEngine = engineConvertFactory.getTemplateEngine(templatePath);
        return templateEngine.templateEngineConvert(target, templatePath);
    }*/
}
