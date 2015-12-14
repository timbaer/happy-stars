package de.waschnick.happy.stars;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@EnableScheduling
@ComponentScan(basePackages = "de.waschnick", excludeFilters = @ComponentScan.Filter(Configuration.class))
public class SwaggerConfig {

    @Bean
    public Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // .groupName("pcp-ideas-star-api")
                .apiInfo(apiInfo())
                // Here we disable auto generating of responses for REST-endpoints
                .useDefaultResponseMessages(true)
                .select()
                .paths(apiPaths())
                .build();
    }

    private Predicate<String> apiPaths() {
        return regex("/api/.*");

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Happy Stars API")
                .description("Create your own universe and fill it with stars! " +
                        "You can create a new universe and add stars. " +
                        "This is an example for a simple REST-CRUD-API for Tests, Demonstrations and Workshops.")
                .termsOfServiceUrl("https://www.axelspringerideas.de")
                //.contact("Axel Springer ideas engineering GmbH")
//                .license("Apache License Version 2.0")
//                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
//                .version("2.0")
                .build();
    }
}
