package com.org.khatabahi.dashboard.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.common.base.request.GenericRequestHeader;
import lombok.Data;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"serviceRequestHeader","serviceRequestBody"})
@Data
@ToString
public class DashboardUserDetailsReq extends GenericRequest<GenericRequestHeader, DashboardUserDetailsReqBody> {
}
