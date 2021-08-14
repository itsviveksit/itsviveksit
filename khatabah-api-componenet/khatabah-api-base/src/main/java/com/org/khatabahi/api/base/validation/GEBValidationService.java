
package com.org.khatabahi.api.base.validation;

import com.org.khatabahi.api.base.annotation.Validate;

import com.org.khatabahi.common.exception.DefaultApiException;
import com.org.khatabahi.logger.KhatabahiLogger;
import org.apache.camel.Exchange;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class GEBValidationService<T> {

    private static final KhatabahiLogger LOGGER = KhatabahiLogger.getLogger(GEBValidationService.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Value(value = "${country_code:#{'SG'}}")
    private String countryCode;

    @Value(value = "${project.version:V1}")
    private String version;


/**
     * This method captures the Validation Constraints Definition defined using the
     * The value specified is a {@link Class} type and should be of type
     * {@link ValidatorConstraint}. The constraint definition instance is looked up
     * in {@link ApplicationContext} by type strategy. Once the instance found and
     * if the validation constraint is enabled from configuration then execute the
     * constraint
     *
     * @param processorClass type of {@link }
     * @param payload
     * @throws
     */

    public void validatePayload(Class processorClass, T payload) throws IOException {
        Class[] args = {Exchange.class};
        List<ValidateMetaData> errorList = new ArrayList<>(0);
        Method executeMethod;
        try{
            executeMethod = processorClass.getDeclaredMethod("execute", args);
        } catch (NoSuchMethodException | SecurityException e) {
            executeMethod = null;
        }
        Validate validate = null;
        if (executeMethod != null && executeMethod.isAnnotationPresent(Validate.class)) {
            validate = executeMethod.getAnnotation(Validate.class);
        }else if(processorClass.isAnnotationPresent(Validate.class)){
            validate = (Validate) processorClass.getAnnotationsByType(Validate.class)[0];
        }
        List<ValidatorConstraint> validationConstraints = Optional.ofNullable(validate)
                .map(Validate::constraints)
                .map(Stream::of)
                .orElse(Stream.empty())
                .map(constraint -> {
                    ValidatorConstraint validatorConstraint;
                    Map<String, ValidatorConstraint> beanMaps = applicationContext.getBeansOfType(ValidatorConstraint.class);
                    if(beanMaps.containsKey(constraint)){
                        validatorConstraint = beanMaps.get(constraint);
                    }else{
                        throw new DefaultApiException("Invalid validatePayload", constraint);
                    }
                    return validatorConstraint;
                })
                .filter(ValidatorConstraint::isEnable)
                .collect(Collectors.toList());

        for(ValidatorConstraint validatorConstraint : validationConstraints){
            errorList.addAll(validatorConstraint.validate(payload));
        }

        if(CollectionUtils.isNotEmpty(errorList)){
            List<String> errorMessageList = Optional.ofNullable(errorList).orElse(Collections.emptyList())
                    .stream().map(meta -> MessageFormat.format("[{0} : {1}]", meta.getErrorCode(), meta.getErrorDescription())).collect(Collectors.toList());
            String errorMessage = String.join(", ", errorMessageList);
            throw new DefaultApiException("invalid parameters", errorMessage);
        }
    }
}

