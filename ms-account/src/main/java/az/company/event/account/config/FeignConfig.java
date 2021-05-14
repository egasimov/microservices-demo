package az.company.event.account.config;

import az.company.event.account.client.CustomerClient;
import feign.Logger;
import feign.codec.ErrorDecoder;
import feign.error.AnnotationErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author QasimovEY on 1/10/2021
 */

@Configuration
@EnableFeignClients("az.company.event.account.client")
public class FeignConfig {

    @Bean
    Logger.Level feignULoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return AnnotationErrorDecoder.builderFor(CustomerClient.class)
                .withResponseBodyDecoder(new JacksonDecoder())
                .build();
    }

}
