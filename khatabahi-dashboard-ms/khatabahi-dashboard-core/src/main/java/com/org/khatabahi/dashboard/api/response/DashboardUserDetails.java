package com.org.khatabahi.dashboard.api.response;

import lombok.*;

import java.util.List;

@Data
public class DashboardUserDetails {
    private String userName;
    private String userId;
    private String userAccount;
    private List<String> otherValue;
}
