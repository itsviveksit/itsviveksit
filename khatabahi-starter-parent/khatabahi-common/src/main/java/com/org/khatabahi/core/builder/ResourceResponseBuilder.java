package com.org.khatabahi.core.builder;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface ResourceResponseBuilder<T, S> extends GenericResponseBuilder<T, S , ResponseEntity<Resource>>{
}
