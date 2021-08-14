package com.org.khatabahi.api.base.validation;

import java.io.IOException;
import java.util.List;

public interface Validator<T> {

    List<ValidateMetaData> validate(T request) throws IOException;
}
