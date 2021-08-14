package com.org.khatabahi.api.base.validation;


import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.common.exception.DefaultApiException;
import com.org.khatabahi.common.utils.InstanceUtils;
import com.org.khatabahi.logger.KhatabahiLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contract Class signature for implementing Business Validation Constraints.
 * Enables the validation constraint based on the configuration. <br>
 * If not configuration available for the constraint then its enabled by
 * default.
 * 
 * 
 * @author Vivek
 *
 * @param <T>
 */
public abstract class ValidatorConstraint<T> implements Validator<T>{
	private static final KhatabahiLogger LOGGER = KhatabahiLogger.getLogger(ValidatorConstraint.class);
	@Autowired
	@Qualifier("spelExpressionParser")
	private ExpressionParser parser;

	@Autowired
	private ValidationConfiguration validationConfiguration;

	@Autowired
	protected SpelExpressionGenerator spelExpressionGenerator;
	private boolean enable = true;

	@PostConstruct
	private void setEnable() {
		if (validationConfiguration.getConstraintsConfig() != null
				&& validationConfiguration.getConstraintsConfig().get(this.getClass().getName()) != null) {
			this.enable = validationConfiguration.getConstraintsConfig().get(this.getClass().getName());
		}
	}

	protected Class<T> getLatestGenericSuperClass(Class<? extends Validator> clazz){
		if(clazz.getGenericSuperclass() instanceof ParameterizedType){
			Type[] params = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
			return (Class) params[0];
		}else {
			return getLatestGenericSuperClass((Class<? extends Validator>)clazz.getGenericSuperclass());
		}
	}

	public boolean isEnable() {
		return enable;
	}

	public abstract void prepareValidationMetaData(T payload, List<ValidateMetaData> validations);

	@Override
	public List<ValidateMetaData> validate(T payload) throws IOException {
		Class<T> clazz = getLatestGenericSuperClass(this.getClass());
		boolean typeSameCheck = clazz.equals(payload.getClass()) || GenericRequest.class.isAssignableFrom(payload.getClass());
		if(payload instanceof String && (!clazz.equals(String.class))){
			payload = InstanceUtils.getObjectMapperInstance().readValue((String)payload, clazz);
		}else if(!typeSameCheck){
			final String errorMessage = MessageFormat.format("Validation {0} occur type to convert issue. the validator type is {1} but you pass type {2}",
					this.getClass().getSimpleName(), clazz.getName(), payload.getClass().getName());
			LOGGER.error(errorMessage);
			throw new DefaultApiException("convert type error", errorMessage);
		}
		return matches(payload);
	}

	private List<ValidateMetaData> matches(T payload) {
		List<ValidateMetaData> validateMetaDataArrayList = new ArrayList<>();
		prepareValidationMetaData(payload, validateMetaDataArrayList);
		commonValidation(validateMetaDataArrayList);

		StandardEvaluationContext context = new StandardEvaluationContext(payload);
		return validateMetaDataArrayList.stream()
		.filter(v -> !parser.parseExpression(v.getSpelExpression()).getValue(context, Boolean.class)).collect(Collectors.toList());
	}

	protected void commonValidation(List<ValidateMetaData> validateMetaDataArrayList) {
		addValidationMetaData(validateMetaDataArrayList, spelExpressionGenerator.isNumeric("serviceRequestBody?.profile?.customerInformation?.userId"), "USER-ID-MISSING",
				"customerInformation.user Id Required and Numeric");
	}

	protected void addValidationMetaData(List<ValidateMetaData> validateMetaDataArrayList, String numeric, String errorCode, String errorDescription) {
		validateMetaDataArrayList.add(new ValidateMetaData(numeric, errorCode, errorDescription));
	}
}
