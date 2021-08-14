package com.org.khatabahi.common.base.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GenericRequestContext {
    @JsonProperty("channelId")
    private String channelId;

     @JsonProperty("countryCode")
     @NotEmpty(message = "countryCode.required")
     private String countryCode;

     @JsonProperty("requesterReferenceNumber")
     private String requesterReferenceNumber;

     @JsonProperty("requestDateTime")
     private String requestDateTime;

     @JsonProperty("requesterUserId")
     private String requesterUserId;

    @JsonProperty("sessionId")
    private String sessionId;
}
