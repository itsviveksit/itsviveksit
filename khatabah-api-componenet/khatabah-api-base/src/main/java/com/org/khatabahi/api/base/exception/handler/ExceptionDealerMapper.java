package com.org.khatabahi.api.base.exception.handler;

import com.org.khatabahi.api.base.exception.annotation.ExceptionDealer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Do the mapping between exception type and exception handler
 */

@Component
public class ExceptionDealerMapper {


/**
     * Check bean com.uob.geb.ng.base.exception.handler.DefaultExceptionHandler
     */

    private static final String DEFAULT_EXCEPTION_HANDLER_BEAN_NAME = "defaultExceptionHandler";

    @Autowired
    private ApplicationContext appContext;

    private Map<Class<? extends Exception>, String> exceptionHandlerMappings;


/**
     * Scan all classes which are annotated with
     * then put them along with the exception type to the mappings(exception, handler) for later usage
     */

    @PostConstruct
    public void init() {
        exceptionHandlerMappings = new HashMap<>();
        Map<String, Object> annotatedBeans = appContext.getBeansWithAnnotation(ExceptionDealer.class);
        annotatedBeans.entrySet().stream().filter(entry -> entry.getValue() instanceof BaseExceptionHandler)
                .forEach(entry -> {
                    String beanName = entry.getKey();
                    final Class beanType = entry.getValue().getClass();
                    ExceptionDealer annotation = (ExceptionDealer)beanType.getAnnotation(ExceptionDealer.class);
                    Class[] exceptions = annotation.exceptionTypes();
                    Arrays.stream(exceptions).forEach(exception -> exceptionHandlerMappings.put(exception, beanName));
                });
    }

    public String getExceptionHandlerByExceptionType(Class<? extends Exception> type) {
        return Optional.ofNullable(exceptionHandlerMappings.get(type)).orElse(DEFAULT_EXCEPTION_HANDLER_BEAN_NAME);
    }
}

