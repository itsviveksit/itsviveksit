package com.org.khatabahi.core.command;

import com.org.khatabahi.common.base.request.GenericRequest;
import com.org.khatabahi.common.base.response.GenericResponse;


public abstract class SimpleCommand<T extends GenericRequest, R extends GenericResponse, S> extends AbstractCommand<T, R, S>{

    @Override
    public void preProcess(T request) {

    }

    @Override
    public R postProcess(R response, T request) {
        return response;
    }
}
