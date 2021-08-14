package com.org.khatabahi.dashboard.service;

import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.dashboard.api.response.DashboardUserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface DashboardService {
    List<DashboardUserDetails> getUserDetailsList(GenericRequest request);
}
