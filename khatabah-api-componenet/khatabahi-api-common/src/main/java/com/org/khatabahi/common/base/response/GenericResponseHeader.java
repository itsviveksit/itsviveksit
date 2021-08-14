package com.org.khatabahi.common.base.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.org.khatabahi.common.base.request.GenericRequestContext;
import lombok.Data;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"responseContext","requestContext", "serviceContext"})
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GenericResponseHeader<RequesterContext extends GenericRequestContext, ResponseContext extends GenericResponseContext> {
    @JsonProperty("requesterContext")
    private RequesterContext requestContext;
    @JsonProperty("responseContext")
    private ResponseContext responseContext;
}
