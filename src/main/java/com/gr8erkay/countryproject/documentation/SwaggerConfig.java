package com.gr8erkay.countryproject.documentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Countries and Cities API")
                        .description("KlashaProject is a task to generate Cities and Countries data. " +
                                "The cities and countries are as is in the world " +
                                "and their data is available for research purposes " +
                                "as well as for information. ")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .contact(new Contact()
                                .email("emmanuelomokayode@gmail.com")
                                .name("KLASHA JAVA ASSESSMENT PROJECT")
                                .url("https://github.com/Gr8erkay")
                        )
                );



    }



}