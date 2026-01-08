package com.eprocure.project.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger configuration.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI eprocureOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("eProcure Backend API")
                        .description("REST API for cloud-native eProcurement Portal")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("eProcure Team")
                                .email("support@eprocure.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development"),
                        new Server()
                                .url("https://api-dev.eprocure.azure.com")
                                .description("Development Environment"),
                        new Server()
                                .url("https://api.eprocure.azure.com")
                                .description("Production Environment")
                ));
    }
}
