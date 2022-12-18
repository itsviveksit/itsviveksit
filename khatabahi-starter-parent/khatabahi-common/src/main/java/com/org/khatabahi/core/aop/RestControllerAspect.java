package com.org.khatabahi.core.aop;

import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.common.base.response.GenericResponse;
import com.org.khatabahi.core.annotation.CommandMapping;
import com.org.khatabahi.core.command.AbstractCommand;
import com.org.khatabahi.core.command.Command;
import com.org.khatabahi.core.validator.Validator;
import com.org.khatabahi.logger.KhatabahiLogger;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class RestControllerAspect {

    private static final KhatabahiLogger log = KhatabahiLogger.getLogger(RestControllerAspect.class);

    @Autowired
    private ApplicationContext context;

    @Value("${country_code}")
    public String countryCode;

    @Pointcut("execution(public * com.org..api.*.*(..))")
    public void restControllerAspect(){

    }

    @Around("restControllerAspect()")
    public Object invokeCommand(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        String target = joinPoint.getSignature().getDeclaringType().getSimpleName()
                + "."
                + joinPoint.getSignature().getName();
        log.info("calling {} : " + target);
        try{
            Method mehtod = ((MethodSignature) joinPoint.getSignature()).getMethod();
            CommandMapping commandMappingAnnotation = mehtod.getAnnotation(CommandMapping.class);
            if(commandMappingAnnotation != null){
                Command<GenericRequest, ?> command = readAnnotation(commandMappingAnnotation);
                GenericRequest request = (GenericRequest) joinPoint.getArgs()[0];
                Object response = null;
                if(null !=command)
                    response = command.execute(request);

                if(!commandMappingAnnotation.skipResponseHandle()){
                    return handleResponse(request, response);
                }else{
                    return response;
                }
            }else{
                log.info("Command not configured invoking controller directly: " + target);
                return joinPoint.proceed();
            }
        }finally {
            log.info("execution time : of ms ", target, (System.currentTimeMillis() - startTime));
        }
    }

    /*
    Reads command mapping frpm annotation
    */
    private Command<GenericRequest,?> readAnnotation(CommandMapping commandMappingAnnotation) {
        String commandBeanName = commandMappingAnnotation.command();
        String validatorBeanName = commandMappingAnnotation.validator();
        log.info("command lookup: " , commandBeanName);
        //Check if command for specific countrycode exits and use it if it does
        //Command<GenericRequest, ?> command = (Command<GenericRequest, GenericResponse>) this.getBean(commandBeanName + countryCode);
        Command<GenericRequest, ?> command = (Command<GenericRequest, GenericResponse>) this.getBean(commandBeanName);
        if(command == null){
            command = (Command<GenericRequest, GenericResponse>) this.getBean(commandBeanName);
        }
        log.info("command found :", command);

        //check the validator is exit or not
        log.info("validatorBeanName: " , validatorBeanName);
        //Validator<GenericRequest> validator = (Validator<GenericRequest>) this.getBean(validatorBeanName + countryCode);
        Validator<GenericRequest> validator = (Validator<GenericRequest>) this.getBean(validatorBeanName);
        if(validator == null){
            validator = (Validator<GenericRequest>) this.getBean(validatorBeanName);
        }

        if(command != null && validator != null){
            log.info("validator found : " , validator.getClass().getName());
            ((AbstractCommand<GenericRequest, GenericResponse, Object>) command).setValidator(validator);
        }
        return command;
    }

    private Object getBean(String beanName) {
        Object bean = null;
        try {
            bean = this.context.getBean(beanName);
        }catch (Exception e){
            if(!countryCode.equalsIgnoreCase("SG")){
                log.warn("error while load the bean name ", beanName);
            }
        }
        return bean;
    }

    private <T> T handleResponse(GenericRequest request, T response) {
        log.info("Executing handleResponse");
        if(response instanceof GenericResponse){
            GenericResponse genericResponse = (GenericResponse) response;
            //setting the header requester context
            if(request.getServiceRequestHeader() != null && request.getServiceRequestHeader().getRequesterContext() != null){
                genericResponse.getServiceResponseHeader().setRequesterContext(request.getServiceRequestHeader().getRequesterContext());
                genericResponse.getServiceResponseHeader().getResponseContext().setResponseDateTime(new Date().toString());
            }
        }
        return response;
    }

}
