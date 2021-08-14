package com.org.khatabahi.common.dbmanagement;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
@Component
@ConfigurationProperties
@EnableConfigurationProperties
public class DatabaseProperties {

    private Map<String, Map<String, String>> databases = new HashMap<>();
    private List<String> transactionContextValue = new ArrayList<>();
    private List<String> nonTradeProductIndicators = new ArrayList<>();
}
