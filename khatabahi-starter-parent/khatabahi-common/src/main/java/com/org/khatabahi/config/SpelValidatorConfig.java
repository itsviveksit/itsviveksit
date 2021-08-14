package com.org.khatabahi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelCompiler;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Configuration
public class SpelValidatorConfig {
    @Bean("spelExpressionParser")
    public SpelExpressionParser getSpelExpressionParser(){
        SpelParserConfiguration configuration = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, getClass().getClassLoader());
        return new SpelExpressionParser(configuration);
    }
}
