package com.org.khatabahi.common.base.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"responseContext","requesterContext", "serviceContext"})
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Setter
@Getter
public class GenericRequest<Header extends GenericRequestHeader, Body extends GenericRequestBody> {
    @JsonProperty("serviceRequestHeader")
    @ApiModelProperty(position = 0, required = true, notes = "Contains ServiceRequestHeader for all services ")
    @Valid
    @NotNull(message = "header.required")
    private Header serviceRequestHeader;

    @JsonProperty("serviceRequestBody")
    @ApiModelProperty(position = 0, required = true, notes = "Contains ServiceRequestBody for all services ")
    @Valid
    @NotNull(message = "body.required")
    private Body serviceRequestBody;
}
