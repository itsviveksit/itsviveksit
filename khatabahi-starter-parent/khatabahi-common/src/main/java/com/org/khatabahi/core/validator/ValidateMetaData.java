package com.org.khatabahi.core.validator;

public class ValidateMetaData {

    private String spelExpression;
    private String errorCode;
    private String defaultMessage;

    public ValidateMetaData(String spelExpression, String errorCode, String defaultMessage) {
        this.spelExpression = spelExpression;
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    public String getSpelExpression() {
        return spelExpression;
    }

    public void setSpelExpression(String spelExpression) {
        this.spelExpression = spelExpression;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String toString() {
        return (new StringBuilder().append(" : ").append(defaultMessage).toString());
    }
}
