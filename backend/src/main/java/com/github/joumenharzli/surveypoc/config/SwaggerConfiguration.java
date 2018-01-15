package com.github.joumenharzli.surveypoc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Configuration
 *
 * @author Joumen HARZLI
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {


  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .groupName("api")
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.github.joumenharzli"))
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Survey API")
        .description("This is a survey api where you can see the list of subject and questions and respond to them")
        .contact(new Contact("Joumen Ali Harzli",
            "https://github.com/joumenharzli/survey-example-spring-boot-angular",
            ""))
        .license("MIT")
        .licenseUrl("https://github.com/joumenharzli/survey-example-spring-boot-angular/blob/master/LICENSE")
        .version("1.0")
        .build();

  }

}
