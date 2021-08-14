package com.org.khatabahi.common.base.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.org.khatabahi.common.base.request.GenericRequestContext;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"responseContext","requestContext", "serviceContext"})
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponseHeader<RequesterContext extends GenericRequestContext, ResponseContext extends GenericResponseContext> {
    @JsonProperty("requesterContext")
    private RequesterContext requesterContext;
    @JsonProperty("responseContext")
    private ResponseContext responseContext;

}
