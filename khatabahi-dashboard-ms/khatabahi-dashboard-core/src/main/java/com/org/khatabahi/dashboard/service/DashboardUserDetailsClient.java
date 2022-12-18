package com.org.khatabahi.dashboard.service;

import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.dashboard.api.response.DashboardUserDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Component
@FeignClient(name = "bbs2k", url = "${userDetails.api.url}")
public interface DashboardUserDetailsClient {

    @PostMapping(path = "${userDetails.api.userDetailsEndpoint}")
    List<DashboardUserDetails> getUserDetails(GenericRequest request);
}
