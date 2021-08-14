package com.org.khatabahi.core.builder;

public interface GenericResponseBuilder<T, S, R> {
    /*To build final api response*/
    R build(T t, S s);
}
