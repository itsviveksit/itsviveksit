package com.org.khatabahi.core.command;

import com.org.khatabahi.common.base.request.GenericRequest;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;

public abstract class ResourceDownloadCommand<T extends GenericRequest, R extends ResponseEntity<Resource>, S> extends AbstractCommand<T, R, S>{

    @Override
    public void preProcess(T request) {

    }

    @Override
    public R postProcess(R response, T request) {
        return response;
    }
}
