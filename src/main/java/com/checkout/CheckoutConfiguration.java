package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;

import java.util.concurrent.Executor;

public interface CheckoutConfiguration {

    IEnvironment getEnvironment();

    SdkCredentials getSdkCredentials();

    HttpClientBuilder getHttpClientBuilder();

    Executor getExecutor();

}
