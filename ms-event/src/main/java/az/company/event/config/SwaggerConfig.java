package az.company.event.config;

import az.company.event.config.properties.ApplicationProperties;
import az.company.event.util.SwaggerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import java.net.URI;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @Author QasimovEY on 26.02.21
 */

@Slf4j
@Configuration
@Import({BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig {

    private final ApplicationProperties.Swagger properties;

    public SwaggerConfig(ApplicationProperties properties) {
        this.properties = properties.getSwagger();
    }

    @Bean
    public Docket docket() {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();

        Docket docket = new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()))
                .paths(regex(properties.getPaths()))
                .build()
                .apiInfo(SwaggerUtil.convertToSpringFoxApiInfo(properties.getApiInfo()))
                .forCodeGeneration(true);

        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .displayRequestDuration(true)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return route(GET("/swagger*"), req ->
                ServerResponse.temporaryRedirect(URI.create("swagger-ui/")).build());
    }
}