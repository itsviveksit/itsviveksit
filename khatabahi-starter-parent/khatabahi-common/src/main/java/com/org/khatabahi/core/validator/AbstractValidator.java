package com.org.khatabahi.core.validator;

import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.common.constant.StatusCode;
import com.org.khatabahi.common.exception.DefaultApiException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;
import java.util.stream.Collectors;

public abstract  class AbstractValidator<T extends GenericRequest> implements Validator<T>{

    @Autowired
    @Qualifier("spelExpressionParser")
    SpelExpressionParser parser;

    @Autowired
    BeanFactory beanFactory;

    private List<ValidateMetaData> validateRequest(T t){
        List<ValidateMetaData> validations = prepareValidationMetaData();
        commonValidation(validations);
        StandardEvaluationContext context = new StandardEvaluationContext(t);
        if(this.beanFactory != null){
            context.setBeanResolver(new BeanFactoryResolver(this.beanFactory));
        }
        return  validations.stream().filter(v -> parser.parseExpression(v.getSpelExpression()).getValue(context, Boolean.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean validate(T request) {
        List<ValidateMetaData> failedValidations = validateRequest(request);
        if(!failedValidations.isEmpty()){
            throw new DefaultApiException(StatusCode.GENERIC_FAILURE_COM_ERR_199.getStatusCode(),
                    failedValidations.stream().map(ValidateMetaData::toString).collect(Collectors.joining(", ")));
        }
        return true;
    }
    protected abstract List<ValidateMetaData> prepareValidationMetaData();

    private List<ValidateMetaData> commonValidation(List<ValidateMetaData> validations) {
        validations.add(new ValidateMetaData("serviceRequestBody?.profile?.customerInformation?.userId == null || serviceRequestBody?.profile?.customerInformation?.userId.isEmpty()", "USER-ID-MISSING", "User Id required"));
        validations.add(new ValidateMetaData("serviceRequestBody?.profile?.customerInformation?.groupId == null || serviceRequestBody?.profile?.customerInformation?.groupId.isEmpty()", "GROUP-ID-MISSING", "Group Id required"));
        return validations;
    }
}
