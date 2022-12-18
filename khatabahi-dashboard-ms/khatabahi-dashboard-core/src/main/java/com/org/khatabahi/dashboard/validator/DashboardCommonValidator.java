package com.org.khatabahi.dashboard.validator;

import com.org.khatabahi.core.validator.AbstractValidator;
import com.org.khatabahi.core.validator.ValidateMetaData;
import com.org.khatabahi.dashboard.api.request.DashboardUserDetailsReq;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component("dashboardCommonValidator")
public class DashboardCommonValidator extends AbstractValidator<DashboardUserDetailsReq> {


    @Override
    protected List<ValidateMetaData> prepareValidationMetaData() {
        List<ValidateMetaData> validateMetaDataArrayList = new ArrayList<>();
        validateMetaDataArrayList.add(new ValidateMetaData("serviceRequestBody?.action == null || serviceRequestBody?.action.isEmpty()", "ACTION-MISSING", "Action is required"));
        return validateMetaDataArrayList;
    }
}
