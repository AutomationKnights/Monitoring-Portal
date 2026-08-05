package com.example.devopsmonitoringportal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI documentation configuration.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI portalOpenApi(BuildInfoProperties buildInfoProperties) {
        return new OpenAPI()
                .info(new Info()
                        .title("DevOps Monitoring Portal API")
                        .description("Operational API endpoints for monitoring dashboard data.")
                        .version(buildInfoProperties.getVersion())
                        .contact(new Contact().name("Platform Engineering Team")));
    }
}
