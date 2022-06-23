package com.luxoft.proj.naceapi.configuration;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = { "com.luxoft.proj.naceapi" },
        entityManagerFactoryRef = "configurationEntityManagerFactory",
        transactionManagerRef = "configurationTransactionManager")
public class DBConfig
{
    @Value("${spring.datasource.max-pool-size}")
    protected Integer maxPoolSize;

    @Value("${spring.datasource.max-lifetime}")
    private Integer maxLifeTime;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;

    @Value("${spring.datasource.user}")
    private String userName;

    @Value("${spring.datasource.pw}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;

    @Bean
    public DataSource getDataSource()
    {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl(url);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setMaxLifetime(maxLifeTime);
        dataSource.setMaximumPoolSize(maxPoolSize);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean configurationEntityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(getDataSource());
        factory.setPackagesToScan("com.luxoft.proj.naceapi");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setDatabasePlatform(hibernateDialect);
        factory.setJpaVendorAdapter(vendorAdapter);
        return factory;
    }

    @Bean
    public PlatformTransactionManager configurationTransactionManager()
    {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(configurationEntityManagerFactory().getObject());
        return txManager;
    }
}