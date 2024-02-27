package com.danisanga.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.danisanga")
public class JdbcConfiguration {

    // TODO Get values from application.properties.
    @Bean
    public DataSource postgrestSQLDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres_rv_database?currentSchema=my_schema");
        dataSource.setUsername("rv_user");
        dataSource.setPassword("rv_password");

        return dataSource;
    }
}
