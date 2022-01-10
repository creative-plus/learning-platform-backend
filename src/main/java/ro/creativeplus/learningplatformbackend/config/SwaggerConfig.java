package ro.creativeplus.learningplatformbackend.config;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  private static final String BEARER_AUTH = "Bearer";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("ro.creativeplus.learningplatformbackend.controller"))
      .paths(PathSelectors.any())
      .build()
      .apiInfo(apiInfo())
      .securitySchemes(securitySchemes())
      .securityContexts(List.of(securityContext()));
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Learning Platform API")
      .description("https://github.com/creative-plus/learning-platform-backend")
      .version("1.0.0")
      .contact(new Contact("Andrei Hagi", "https://github.com/haginus", "hagiandrei.ah@gmail.com"))
      .build();
  }

  private List<SecurityScheme> securitySchemes() {
    return List.of(new ApiKey(BEARER_AUTH, "Authorization", "header"));
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
      .securityReferences(List.of(bearerAuthReference()))
      .forPaths(
        Predicates.or(
          PathSelectors.ant("/auth/user"),
          PathSelectors.ant("/trainees/**"),
          PathSelectors.ant("/projects/**"),
          PathSelectors.ant("/courses/**"),
          PathSelectors.ant("/leaderboard/**"),
          PathSelectors.ant("/progress/**"),
          PathSelectors.ant("/media/**")
        )
      ).build();
  }

  private SecurityReference bearerAuthReference() {
    return new SecurityReference(BEARER_AUTH, new AuthorizationScope[0]);
  }
}