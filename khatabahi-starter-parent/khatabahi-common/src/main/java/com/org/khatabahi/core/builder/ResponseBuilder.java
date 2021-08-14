package com.org.khatabahi.core.builder;

import com.org.khatabahi.common.base.response.GenericResponse;

public interface ResponseBuilder<T, S> extends GenericResponseBuilder<T, S, GenericResponse> {
    @Override
    GenericResponse build(T t, S s);
}
