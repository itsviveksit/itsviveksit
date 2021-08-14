package com.org.khatabahi.api.base.config;

import com.org.khatabahi.api.base.exception.handler.ExceptionHandlerFactory;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.apache.camel.processor.RedeliveryPolicy;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Configuration
public class BeanConfiguration {

    @Bean
    public DeadLetterChannelBuilder deadLetterErrorHandler(){
        DeadLetterChannelBuilder deadLetterChannelBuilder = new DeadLetterChannelBuilder();
        deadLetterChannelBuilder.setDeadLetterUri("direct:error");
        deadLetterChannelBuilder.setRedeliveryPolicy(reDeliveryPolicy());
        deadLetterChannelBuilder.useOriginalMessage();
        return deadLetterChannelBuilder;
    }

    @Bean
    public RedeliveryPolicy reDeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.allowRedeliveryWhileStopping(false);
        redeliveryPolicy.maximumRedeliveryDelay(1000);
        redeliveryPolicy.maximumRedeliveries(0);
        redeliveryPolicy.retryAttemptedLogLevel(LoggingLevel.INFO);
        return redeliveryPolicy;
    }

    @Bean("exceptionHandlerFactory")
    public FactoryBean serviceLocatorFactoryBean(){
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(ExceptionHandlerFactory.class);
        return factoryBean;
    }

    @Bean("spelExpressionParser")
    public ExpressionParser expressionParser(){
        SpelParserConfiguration config = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, getClass().getClassLoader());
        return new SpelExpressionParser(config);
    }
}
