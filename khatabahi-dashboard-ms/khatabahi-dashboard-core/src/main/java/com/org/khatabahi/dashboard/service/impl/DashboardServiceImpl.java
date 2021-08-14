package com.org.khatabahi.dashboard.service.impl;

import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.dashboard.api.response.DashboardUserDetails;
import com.org.khatabahi.dashboard.service.DashboardService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DashboardServiceImpl implements DashboardService {



    @Override
    public List<DashboardUserDetails> getUserDetailsList(GenericRequest request) {
        List<DashboardUserDetails> dashboardUserDetailsList = new ArrayList<>();
        DashboardUserDetails dashboardUserDetails = new DashboardUserDetails();
        dashboardUserDetails.setUserName("vivek");
        dashboardUserDetailsList.add(dashboardUserDetails);
        return dashboardUserDetailsList;
    }
}
