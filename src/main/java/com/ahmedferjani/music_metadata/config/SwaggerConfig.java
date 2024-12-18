package com.ahmedferjani.music_metadata.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Music Metadata API",
        version = "0.0.1-SNAPSHOT",
        description = "REST-API for requesting metadata of a music track from an external service storing it in a database",
        contact = @Contact(name = "Ahmed Ferjani", email = "ferjaniahmed1997@gmail.com")))
public class SwaggerConfig {
}