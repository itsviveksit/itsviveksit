/*
 * System Name         : GEBNexGen
 * Program Id          : uob-gebng
 * Author              : tmpil9
 * Date                : 14/07/2019
 * Copyright (c) United Overseas Bank Limited Co.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * United Overseas Bank Limited Co. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with
 * United Overseas Bank Limited Co.
 *//*


package com.org.khatabahi.api.base.services.impl;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uob.geb.ng.common.exception.DefaultApiException;
import com.uob.geb.ng.base.services.TemplateEngineConvert;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;

import static com.uob.geb.ng.base.services.TemplateEngineConvert.VELOCITY_TEMPLATE_ENGINE_CONVERT;

*/
/**
 * @author tmpil9
 * @version 1.0
 * @date 14/07/2019
 *//*

@Service(VELOCITY_TEMPLATE_ENGINE_CONVERT)
public class VelocityTemplateEngineConvertImpl implements TemplateEngineConvert {
    private final static ObjectMapper MAPPER = new ObjectMapper().disable(MapperFeature.USE_ANNOTATIONS);
    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public String templateEngineConvert(Object target, String templatePath) {
        try {
            String jsonStr;
            if (target instanceof String) {
                jsonStr = (String)target;
            } else {
                jsonStr = MAPPER.writeValueAsString(target);
            }
            JSONObject objectNode = new JSONObject(jsonStr);
            Template t = velocityEngine.getTemplate(templatePath);
            VelocityContext context = new VelocityContext();
            context.put("body", objectNode);
            StringWriter writer = new StringWriter();
            t.merge( context, writer );
            return writer.toString();
        } catch (IOException | JSONException e) {
            throw new DefaultApiException(e.getMessage());
        }
    }
}
*/
