package com.org.khatabahi.common.dbmanagement;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {

    @Bean(DbConfigConstant.KHATABAHI_JDBC_TEMPLATE)
    @Primary
    @ConditionalOnProperty(prefix = "databases.khatabahi", name = "name")
    public JdbcTemplate khatabahiJdbcTemplate(@Qualifier(DbConfigConstant.KHATABAHI_DATA_SOURCE) DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean(DbConfigConstant.KHATABAHI_NAMED_JDBC_TEMPLATE)
    @Primary
    @ConditionalOnProperty(prefix = "databases.khatabahi", name = "name")
    public NamedParameterJdbcTemplate khatabahiNamedJdbcTemplate(@Qualifier(DbConfigConstant.KHATABAHI_DATA_SOURCE) DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "databases.khatabahi", name = "name")
    public DataSourceTransactionManager khatabahiTransactionManager(@Qualifier(DbConfigConstant.KHATABAHI_DATA_SOURCE) DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
}
