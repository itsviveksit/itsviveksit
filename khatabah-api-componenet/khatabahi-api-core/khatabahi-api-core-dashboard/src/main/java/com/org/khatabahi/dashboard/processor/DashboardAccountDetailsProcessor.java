package com.org.khatabahi.dashboard.processor;

import com.org.khatabahi.api.base.annotation.Validate;
import com.org.khatabahi.api.base.processor.SimpleProcessor;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;



@Component("dashboardAccountDetailsProcessor")
public class DashboardAccountDetailsProcessor extends SimpleProcessor<String, String> {
    @Override
    @Validate(constraints = "dashboardCommonValidator")
    public String execute(Exchange exchange) {
        System.out.println("----------------------dashboard----------");
        return producerTemplate.requestBodyAndHeaders("{{ms.url.dashboard.details}}",getBody(exchange), getHeaders(exchange), String.class);
    }

  /*  @Override
    public void postProcess(Exchange exchange, String request, String result) throws IOException {
        if(result != null){
            throw new DefaultApiException(StatusCode.GENERIC_FAILURE_COM_ERR_199);
        }
    }*/
}
