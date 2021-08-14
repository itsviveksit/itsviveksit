package com.org.khatabahi.common.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InstanceUtils {

    public static ObjectMapper getObjectMapperInstance(){
        return ObjectMapperInstance.INSTANCE.getObjectMapper();
    }

    enum ObjectMapperInstance {
        INSTANCE;
        private ObjectMapper objectMapper;
        ObjectMapperInstance(){
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            this.objectMapper = mapper;
        }
        public ObjectMapper getObjectMapper(){
            return objectMapper;
        }
    }
}
