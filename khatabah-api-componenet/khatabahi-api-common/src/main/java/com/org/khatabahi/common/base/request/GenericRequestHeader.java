package com.org.khatabahi.common.base.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"responseContext","requesterContext", "serviceContext"})
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GenericRequestHeader<RequestContext extends GenericRequestContext> {
    @JsonProperty("requesterContext")
    @Valid
    @NotNull(message = "requesterContext.required")
    private RequestContext requestContext;
}
