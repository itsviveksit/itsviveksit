package com.org.khatabahi.api.base.validation;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Configuration Place Holder class for maintaining configuration pertain to
 * Business Constraint Validation
 * 
 * @author Vivek
 *
 */
@Component
@ConfigurationProperties(prefix = "validation.constraint")
public class ValidationConfiguration {

	private Map<String, Boolean> constraintsConfig;

	public Map<String, Boolean> getConstraintsConfig() {
		return constraintsConfig;
	}

	public void setConstraintsConfig(Map<String, Boolean> constraintsConfig) {
		this.constraintsConfig = constraintsConfig;
	}
}
