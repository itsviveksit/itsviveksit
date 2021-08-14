package com.org.khatabahi.core.validator;

import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.common.base.request.GenericRequestBody;
import com.org.khatabahi.common.base.request.GenericRequestHeader;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("commonValidator")
public class CommonValidator extends AbstractValidator<GenericRequest<GenericRequestHeader, GenericRequestBody>> {
    @Override
    protected List<ValidateMetaData> prepareValidationMetaData() {
        return new ArrayList<>();
    }
}
