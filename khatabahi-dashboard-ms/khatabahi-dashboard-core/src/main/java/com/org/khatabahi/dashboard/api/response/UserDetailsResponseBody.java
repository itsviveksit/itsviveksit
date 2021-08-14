package com.org.khatabahi.dashboard.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.org.khatabahi.common.base.GenericProfile;
import com.org.khatabahi.common.base.response.GenericResponseBody;
import lombok.Data;

import java.util.List;
@Data
public class UserDetailsResponseBody extends GenericResponseBody<GenericProfile> {

    @JsonProperty("userDetailsList")
    private List<DashboardUserDetails> userDetailsList;
}
