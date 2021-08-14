package com.org.khatabahi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "validator")
@PropertySource("classpath:validatorConfig.properties")
public class ValidatorConfig {

    private Map<String, Boolean> config = new HashMap<>();

    public Map<String, Boolean> getConfig() {
        return config;
    }

    public void setConfig(Map<String, Boolean> config) {
        this.config = config;
    }
}
