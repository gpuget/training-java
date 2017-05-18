package com.excilys.cdb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.excilys.cdb.persistence")
@EnableTransactionManagement
@PropertySource("classpath:default.properties")
public class DataConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataConfig.class);

    @Value("${db.driver}")
    private String driver;

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    /**
     * Gets the data source bean.
     *
     * @return data source
     */
    @Bean
    public DriverManagerDataSource dataSource() {
        LOGGER.info("new DataSource");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        LOGGER.debug("Data source : " + dataSource);

        return dataSource;
    }

    /**
     * Gets the transaction manager.
     *
     * @return transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        LOGGER.info("new TransactionManager");
        return new DataSourceTransactionManager(dataSource());
    }
}
