package com.backendpractice.blog.config;

import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

  public static final String AUTHORIZATION_HEADER = "Authorization";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getInfo())
        .securityContexts(securityContexts())
        .securitySchemes(List.of(apiKeys()))
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo getInfo() {
    return new ApiInfo(
        "Blogging application : Backend Course",
        "This project is developed by Ravi Raj Singh",
        "1.0",
        "Terms of Service",
        new Contact(
            "Ravi Raj singh",
            "abc@qwerty.com",
            "ravi.official097@gmail.com"),
        "License of ApiInfo",
        "abc.com",
        Collections.emptyList()
    );
  }

  private ApiKey apiKeys() {
    return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
  }

  private List<SecurityContext> securityContexts() {
    return List.of(SecurityContext.builder()
        .securityReferences(securityReferences())
        .build());
  }

  private List<SecurityReference> securityReferences() {
    var scopes = new AuthorizationScope("global", "accessEverything");
    return List.of(new SecurityReference("JWT", new AuthorizationScope[]{scopes}));
  }

}
