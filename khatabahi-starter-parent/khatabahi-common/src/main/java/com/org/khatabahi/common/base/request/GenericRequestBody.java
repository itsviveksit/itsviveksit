package com.org.khatabahi.common.base.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.org.khatabahi.common.base.GenericProfile;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"responseContext","requesterContext", "serviceContext"})
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GenericRequestBody<Profile extends GenericProfile> {
    @JsonProperty("profile")
    @Valid
    @NotNull(message = "profile.required")
    private Profile profile;

    @Builder
    public static <Profile extends GenericProfile>  GenericRequestBody<Profile> build(Profile profile){
        GenericRequestBody<Profile> requestBody = new GenericRequestBody<>();
        requestBody.setProfile(profile);
        return requestBody;
    }
}
