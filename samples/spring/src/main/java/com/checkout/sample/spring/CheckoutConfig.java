package com.checkout.sample.spring;

import com.checkout.ApiClient;
import com.checkout.ApiClientImpl;
import com.checkout.CheckoutApi;
import com.checkout.CheckoutApiImpl;
import com.checkout.CheckoutConfiguration;
import com.checkout.Environment;
import com.checkout.GsonSerializer;
import com.checkout.Serializer;
import com.checkout.Transport;
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
        return new CheckoutConfiguration(publicKey, secretKey, Environment.SANDBOX);
    }

    @Bean
    public Serializer serializer() {
        return new GsonSerializer();
    }

    @Bean
    public Transport transport(final CheckoutConfiguration checkoutConfiguration, final RestTemplate restTemplate) {
        return new RestTemplateTransport(checkoutConfiguration.getUri(), restTemplate);
    }

    @Bean
    public ApiClient apiClient(final Serializer serializer, final Transport transport) {
        return new ApiClientImpl(serializer, transport);
    }

    @Bean
    public CheckoutApi checkoutApi(final ApiClient apiClient, final CheckoutConfiguration checkoutConfiguration) {
        return new CheckoutApiImpl(apiClient, checkoutConfiguration);
    }
}
