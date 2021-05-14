package az.company.card.config;

import az.company.card.adapter.out.external.EventPublisherClient;
import feign.Logger;
import feign.codec.ErrorDecoder;
import feign.error.AnnotationErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.okhttp.OkHttpClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author QasimovEY on 02.03.21
 */
@Configuration
@EnableFeignClients(basePackages = "az.company.card.adapter.out.external")
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
        return AnnotationErrorDecoder.builderFor(EventPublisherClient.class)
                .withResponseBodyDecoder(new JacksonDecoder())
                .build();
    }

/*    @Bean
    public Client feignClient() {
        Client trustSSLSockets = new Client.Default(getSSLSocketFactory(), new NoopHostnameVerifier());
        return trustSSLSockets;
    }*/


/*
    private SSLSocketFactory getSSLSocketFactory() {
        try {
            TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            };

            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            return sslContext.getSocketFactory();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
*/

}
