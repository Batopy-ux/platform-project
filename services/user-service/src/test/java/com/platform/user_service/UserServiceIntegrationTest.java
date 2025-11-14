package com.platform.user_service;


import com.platform.user_service.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class UserServiceIntegrationTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>()
            .withDatabaseName("userdb")
            .withUsername("postgres")
            .withPassword("postgres");

    static {
        postgres.start();
    }

    @DynamicPropertySource
    static void registerProps(DynamicPropertyRegistry r){
        r.add("spring.datasource.url", postgres::getJdbcUrl);
        r.add("spring.datasource.username", postgres::getUsername);
        r.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoadsAndFlywayRuns() {
        // After startup Flyway should have created the table
        long count = userRepository.count();
        // no assertions necessary; just ensuring repository works
    }
}

