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
 *//*


package com.org.khatabahi.api.base.processor;

import com.uob.geb.ng.base.constant.ProcessorConstants;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

import java.io.IOException;

*/
/**
 * @author tmpil9
 * @version 1.0
 * @date 08/07/2019
 *//*

@Component("simpleFinaRestProcessor")
public class SimpleFinaRestProcessor extends SimpleProcessor<String, String> {

    @Override
    public String execute(Exchange exchange) throws IOException {
        String finReq = exchange.getProperty(ProcessorConstants.FIN_TEMPLATE_REQ, String.class);
        String finRes = exchange.getProperty(ProcessorConstants.FIN_TEMPLATE_RES, String.class);
        String finEndpoint = exchange.getProperty(ProcessorConstants.FIN_ENDPOINT, String.class);

        String body = getExchangeBody(exchange);
        String convertedBodyStr = templateConvert(exchange, body, finReq);

        String fetchResponseStr = producerTemplate.requestBodyAndHeaders(finEndpoint, convertedBodyStr, getHeaders(exchange), String.class);

        return templateConvert(exchange, fetchResponseStr, finRes);

    }
}
*/
