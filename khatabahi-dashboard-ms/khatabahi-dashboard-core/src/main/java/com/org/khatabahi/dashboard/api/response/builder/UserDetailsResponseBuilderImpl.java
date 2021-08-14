package com.org.khatabahi.dashboard.api.response.builder;

import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.common.base.request.GenericRequestBody;
import com.org.khatabahi.common.base.request.GenericRequestHeader;
import com.org.khatabahi.common.base.response.GenericResponse;
import com.org.khatabahi.common.constant.StatusCode;
import com.org.khatabahi.core.builder.CommonResponseBuilder;
import com.org.khatabahi.core.builder.ResponseBuilder;
import com.org.khatabahi.dashboard.api.response.DashboardUserDetails;
import com.org.khatabahi.dashboard.api.response.UserDetailsResponseBody;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("userDetailsResponseBuilderImpl")
public class UserDetailsResponseBuilderImpl implements ResponseBuilder<GenericRequest<GenericRequestHeader, GenericRequestBody>, List<DashboardUserDetails>> {
    @Override
    public GenericResponse build(GenericRequest<GenericRequestHeader, GenericRequestBody> request, List<DashboardUserDetails> dashboardUserDetails) {
        UserDetailsResponseBody userDetailsResponseBody = new UserDetailsResponseBody();
        userDetailsResponseBody.setUserDetailsList(dashboardUserDetails);
        return CommonResponseBuilder.build(request, StatusCode.GENERIC_SUCCESS_COM_200.getStatusCode(),StatusCode.GENERIC_SUCCESS_COM_200.getStatusDescription(),
                requestBody -> {
                    userDetailsResponseBody.setProfile(request.getServiceRequestBody().getProfile());
                    return userDetailsResponseBody;
                });
    }
}
