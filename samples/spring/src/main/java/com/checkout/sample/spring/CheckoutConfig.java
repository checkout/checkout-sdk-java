package com.checkout.sample.spring;

import com.checkout.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CheckoutConfig {
    @Value("checkout.secretKey")
    private String secretKey;
    @Value("checkout.publicKey")
    private String publicKey;

    @Bean
    public CheckoutConfiguration checkoutConfiguration() {
        return new CheckoutConfiguration(secretKey, true, publicKey);
    }

    @Bean
    public Serializer serializer() {
        return new GsonSerializer();
    }

    @Bean
    public Transport transport(CheckoutConfiguration checkoutConfiguration, RestTemplate restTemplate) {
        return new RestTemplateTransport(checkoutConfiguration.getUri(), restTemplate);
    }

    @Bean
    public ApiClient apiClient(Serializer serializer, Transport transport) {
        return new ApiClientImpl(serializer, transport);
    }

    @Bean
    public CheckoutApi checkoutApi(ApiClient apiClient, CheckoutConfiguration checkoutConfiguration) {
        return new CheckoutApiImpl(apiClient, checkoutConfiguration);
    }
}
