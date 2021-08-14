package com.org.khatabahi.api.base.validation;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ValidateMetaData {

    private String spelExpression;
    private String errorCode;
    private String errorDescription;
}
