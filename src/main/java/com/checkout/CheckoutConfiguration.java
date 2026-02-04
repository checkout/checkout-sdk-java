package com.checkout;

import java.util.concurrent.Executor;

import org.apache.http.impl.client.HttpClientBuilder;

public interface CheckoutConfiguration {

    IEnvironment getEnvironment();

    EnvironmentSubdomain getEnvironmentSubdomain();

    SdkCredentials getSdkCredentials();

    HttpClientBuilder getHttpClientBuilder();

    Executor getExecutor();

    TransportConfiguration getTransportConfiguration();

    Boolean isTelemetryEnabled();

    Boolean isSynchronous();

    Resilience4jConfiguration getResilience4jConfiguration();

}
