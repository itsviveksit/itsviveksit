package com.org.khatabahi.common.util;

import lombok.Data;

@Data
public class TenantContext {
    private TenantContext() {
    }

    private static ThreadLocal<Object> currentTenant = new ThreadLocal<>();

    public static ThreadLocal<Object> getCurrentTenant() {
        return currentTenant;
    }

    public static void setCurrentTenant(ThreadLocal<Object> currentTenant) {
        TenantContext.currentTenant = currentTenant;
    }

    public static void clearCurrentTenant(){
        currentTenant.remove();
    }
}
