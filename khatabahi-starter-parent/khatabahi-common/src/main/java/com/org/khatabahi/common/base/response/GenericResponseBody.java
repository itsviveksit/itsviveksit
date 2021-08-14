package com.org.khatabahi.common.base.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.org.khatabahi.common.base.GenericProfile;
import lombok.Data;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"profile"})
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GenericResponseBody<Profile extends GenericProfile> {
    @JsonProperty("profile")
    private Profile profile;
}
