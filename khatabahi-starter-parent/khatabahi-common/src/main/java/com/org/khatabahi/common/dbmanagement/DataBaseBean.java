package com.org.khatabahi.common.dbmanagement;

import lombok.Data;

@Data
public class DataBaseBean {
    private String name;
    private String driver;
    private String walletLocation;
    private String userId;
    private String sid;
    private String dbUser;
    private String tnsLocation;
    private String dbUrl;
    /*
    * readTimeout helps to read timeout while reading from the socket */
    private String readTimeout;
    /*This */
    private Long hikariConnectionTimeOut;
    private Integer hikariMaximumPoolSize;
    private Integer hikariMinimumIdle;
    private String hikariPoolName;
    private Long hikariLeakDetectionThreshold;
}
