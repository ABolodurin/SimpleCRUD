package ru.bolodurin.crud.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;


@Configuration
public class SwaggerConfig {
    public static final String TAG = "Business object resource";

    @Value(value = "${application.swagger.info.title}")
    private String title;
    @Value(value = "${application.swagger.info.description}")
    private String description;
    @Value(value = "${application.swagger.info.version}")
    private String version;
    @Value(value = "${application.swagger.info.contact.name}")
    private String name;
    @Value(value = "${application.swagger.info.contact.email}")
    private String email;
    @Value(value = "${application.swagger.info.contact.url}")
    private String url;


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .version(version)
                        .contact(new Contact()
                                .name(name)
                                .email(email)
                                .url(url)));
    }

}
