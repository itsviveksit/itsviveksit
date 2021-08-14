package com.org.khatabahi.common.base.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"serviceResponseHeader","serviceResponseBody"})
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class GenericResponse<Header extends GenericResponseHeader, Body extends GenericResponseBody> {
    @JsonProperty("serviceRequestHeader")
    private Header serviceResponseHeader;

    @JsonProperty("serviceResponseBody")
    private Body serviceResponseBody;
}
