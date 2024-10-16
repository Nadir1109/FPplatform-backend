package com.freelanceplatform.freelanceplatform.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;


public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Freelance Platform API")
                        .version("1.0")
                        .description("API documentatie voor het Freelance Platform project"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("freelance-platform")
                .pathsToMatch("/api/**")
                .build();
    }
}
