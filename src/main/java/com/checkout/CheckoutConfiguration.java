package com.checkout;

import java.util.concurrent.Executor;

import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;

public interface CheckoutConfiguration {

    IEnvironment getEnvironment();

    EnvironmentSubdomain getEnvironmentSubdomain();

    SdkCredentials getSdkCredentials();

    HttpClientBuilder getHttpClientBuilder();

    Executor getExecutor();

    TransportConfiguration getTransportConfiguration();

    Boolean isTelemetryEnabled();

}
