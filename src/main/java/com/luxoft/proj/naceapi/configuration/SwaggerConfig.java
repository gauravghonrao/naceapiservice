package com.luxoft.proj.naceapi.configuration;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Nace API Service", version = "3.0", description = "Nace API Service Information"))
public class SwaggerConfig
{
}
