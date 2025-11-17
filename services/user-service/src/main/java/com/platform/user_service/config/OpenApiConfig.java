package com.platform.user_service.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@SecurityScheme(name="bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class OpenApiConfig {
    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> openApi.info(new Info()
                .title("User Service API")
                .version("v1.0")
                .description("User service endpoints for registration, authentication and profile.")
                .contact(new Contact().name("User Service").email("user-service@example.com"))
                .license(new License().name("MIT License")));
    }
}
