package com.org.khatabahi.dashboard.validator;

import com.org.khatabahi.api.base.validation.ValidateMetaData;
import com.org.khatabahi.api.base.validation.ValidatorConstraint;
import com.org.khatabahi.dashboard.request.DashboardUserDetailsReq;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("dashboardCommonValidator")
public class DashboardCommonValidator extends ValidatorConstraint<DashboardUserDetailsReq> {
    @Override
    public void prepareValidationMetaData(DashboardUserDetailsReq payload, List<ValidateMetaData> validations) {
        addValidationMetaData(validations,
                spelExpressionGenerator.isNotEmptyString("serviceRequestBody?.action"), "ACTION-MISSING",
                        "action Id Required and Numeric");
    }
}
