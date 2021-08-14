package com.org.khatabahi.common.constant;

import org.apache.commons.lang3.builder.ToStringBuilder;

public enum StatusCode {
    GENERIC_SUCCESS_COM_200("COM-200", "sucesss"),
    GENERIC_FAILURE_COM_ERR_199("PMT_199", "sucesss");

    private String statusCode;
    private String statusDescription;

    StatusCode(String statusCode, String statusDescription) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
    }

    public String getStatusCode(){
        return statusCode;
    }

    public String getStatusDescription(){
        return statusDescription;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("statusCode", statusCode)
                .append("statusDescription", statusDescription)
                .toString();
    }
}
