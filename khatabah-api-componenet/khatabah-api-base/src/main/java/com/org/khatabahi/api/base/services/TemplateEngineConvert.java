package com.org.khatabahi.api.base.services;

/**
 * @author tmpil9
 * @version 1.0
 * @date 14/07/2019
 */
public interface TemplateEngineConvert {
    String VELOCITY_TEMPLATE_ENGINE_CONVERT= "velocityTemplateEngineConvert";
    String FREEMARKER_TEMPLATE_ENGINE_CONVERT= "freemarkerTemplateEngineConvert";

    String templateEngineConvert(Object target, String templatePath);
}
