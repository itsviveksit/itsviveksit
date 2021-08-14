package com.org.khatabahi.api.base.validation;

import org.springframework.stereotype.Component;

@Component
public class SpelExpressionGenerator {

    private static final String APACHE_STRING_UTILS = "T(org.apache.commons.lang3.StringUtils)";
    private static final String APACHE_COLLECTION_UTILS = "T(org.apache.commons.collections.CollectionUtils)";

    public String isNumeric(String filed){
        return new StringBuilder(APACHE_STRING_UTILS)
                .append(".isNumeric(")
                .append(filed)
                .append(")")
                .toString();
    }
    public String isNotEmptyString(String filed){
        return new StringBuilder(APACHE_STRING_UTILS)
                .append(".isNotBlank(")
                .append(filed)
                .append(")")
                .toString();
    }
    public String isEmptyString(String filed){
        return new StringBuilder(APACHE_STRING_UTILS)
                .append(".isBlank(")
                .append(filed)
                .append(")")
                .toString();
    }
    public String isNotEmptyArray(String filed){
        return new StringBuilder(filed)
                .append("?.size() > 0")
                .toString();
    }
}
