package com.org.khatabahi.dashboard.command;

import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.common.base.request.GenericRequestBody;
import com.org.khatabahi.common.base.request.GenericRequestHeader;
import com.org.khatabahi.common.base.response.GenericResponse;
import com.org.khatabahi.core.builder.ResponseBuilder;
import com.org.khatabahi.core.command.SimpleCommand;
import com.org.khatabahi.dashboard.api.response.DashboardUserDetails;
import com.org.khatabahi.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("userDetailsListCommand")
public class UserDetailsListCommand extends SimpleCommand<GenericRequest<GenericRequestHeader, GenericRequestBody>, GenericResponse, List<DashboardUserDetails>> {


    @Autowired
    private DashboardService dashboardService;

    @Autowired
    @Qualifier("userDetailsResponseBuilderImpl")
    private ResponseBuilder<GenericRequest<GenericRequestHeader, GenericRequestBody>, List<DashboardUserDetails>> responseBuilder;


    @Override
    public List<DashboardUserDetails> process(GenericRequest<GenericRequestHeader, GenericRequestBody> request) {
        return dashboardService.getUserDetailsList(request);
    }

    @Override
    public GenericResponse prepareResponse(GenericRequest<GenericRequestHeader, GenericRequestBody> request, List<DashboardUserDetails> response) {
        return responseBuilder.build(request, response);
    }
}
