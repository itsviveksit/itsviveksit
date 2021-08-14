/*
package com.org.khatabahi.app;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class CamelRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        System.out.println("-----------------------aaa-----------");
        restConfiguration().component("servlet").port(9090).host("localhost").bindingMode(RestBindingMode.json);
        rest().get("/hello-world").produces(MediaType.APPLICATION_JSON_VALUE).route().setBody(constant("welcome to fucking duniya"));
    }

}
*/
