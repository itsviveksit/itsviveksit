package com.org.khatabahi.core.command;

import com.org.khatabahi.common.base.request.GenericRequest;

public interface Command<T extends GenericRequest, R> {
    /*
    * Method declaration for command implementation
    */
    R execute(T request);
}
