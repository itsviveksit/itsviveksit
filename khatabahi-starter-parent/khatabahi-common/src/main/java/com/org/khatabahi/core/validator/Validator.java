package com.org.khatabahi.core.validator;

import com.org.khatabahi.common.base.request.GenericRequest;

public interface Validator<T extends GenericRequest> {
    /*
    * To Validate input request
    * @param request
    * @return boolean
    * */
    boolean validate(T request);
}
