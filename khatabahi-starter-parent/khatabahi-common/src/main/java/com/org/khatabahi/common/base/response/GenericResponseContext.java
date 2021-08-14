package com.org.khatabahi.common.base.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GenericResponseContext {
    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("statusDescription")
    private String statusDescription;

    @JsonProperty("responseDateTime")
    private String responseDateTime;

    @JsonProperty("traceId")
    private String traceId;

    public GenericResponseContext() {
    }

    @Builder
    public GenericResponseContext(String statusCode, String statusDescription, String responseDateTime) {
        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
        this.responseDateTime = responseDateTime;
    }
}
