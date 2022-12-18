package com.org.khatabahi.dashboard.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.org.khatabahi.common.base.GenericProfile;
import com.org.khatabahi.common.base.request.GenericRequestBody;
import lombok.Data;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DashboardUserDetailsReqBody extends GenericRequestBody<GenericProfile> {
    @JsonProperty("action")
    public String action;
}
