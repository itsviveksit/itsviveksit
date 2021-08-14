package com.org.khatabahi.common.dbmanagement;

import com.org.khatabahi.common.exception.DefaultApiException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import oracle.jdbc.pool.OracleDataSource;
import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class MultitenantConfiguration {

    @Autowired
    private DatabaseProperties databaseProperties;

    @Bean(DbConfigConstant.KHATABAHI_JDBC_TEMPLATE)
    @Primary
    @ConfigurationProperties(prefix = "databases.khatabahi")
    @ConditionalOnProperty(prefix = "databases.khatabahi", name = "name")
    public DataBaseBean primaryDataBaseBean(){
        return new DataBaseBean();
    }

    @Bean(DbConfigConstant.KHATABAHI_DATA_SOURCE)
    @ConditionalOnBean(name = DbConfigConstant.KHATABAHI_DATA_BEAN)
    public DataSource getDataSource(@Qualifier(DbConfigConstant.KHATABAHI_DATA_BEAN) DataBaseBean dataBaseBean){
        try{
            return oracleDataSourceConfig(dataBaseBean);
        }catch (Exception e){
            throw new DefaultApiException("data base connection iss", e);
        }
    }

    private DataSource oracleDataSourceConfig(DataBaseBean dataBaseBean) throws SQLException, UnknownHostException {
        OracleDataSource oracleDataSource = new OracleDataSource();
        String tenantId = dataBaseBean.getName();
        String sUserId = dataBaseBean.getUserId();
        String sid = dataBaseBean.getSid();
        String dbUser = dataBaseBean.getDbUser();
        String sLocation = dataBaseBean.getWalletLocation();

        InetAddress inetAddress = InetAddress.getLocalHost();
        String sHostName = inetAddress.getHostName();
        String sCanonicalHostName = inetAddress.getCanonicalHostName();
        String walletLocation = sLocation + sUserId + sid + sHostName;
        String tnsLocation = dataBaseBean.getTnsLocation();

        Properties properties = new Properties();
        properties.setProperty("oracle.net.wallet_location", walletLocation);
        properties.setProperty("oracle.jdbc.ReadTimeout", dataBaseBean.getReadTimeout());

        oracleDataSource.setConnectionProperties(properties);
        oracleDataSource.setURL(dataBaseBean.getDbUrl());

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSource(oracleDataSource);
        hikariConfig.setConnectionTimeout(dataBaseBean.getHikariConnectionTimeOut());
        hikariConfig.setMaximumPoolSize(dataBaseBean.getHikariMaximumPoolSize());
        hikariConfig.setMinimumIdle(dataBaseBean.getHikariMinimumIdle());
        hikariConfig.setPoolName(dataBaseBean.getHikariPoolName());
        hikariConfig.setDriverClassName(dataBaseBean.getDriver());
        hikariConfig.setLeakDetectionThreshold(dataBaseBean.getHikariLeakDetectionThreshold());
       return new HikariDataSource(hikariConfig);
    }
}
