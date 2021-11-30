package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;
import java.util.concurrent.Executor;

public interface CheckoutConfiguration {

    URI getBaseUri();

    SdkCredentials getSdkCredentials();

    HttpClientBuilder getHttpClientBuilder();

    Executor getExecutor();

    FilesApiConfiguration getFilesApiConfiguration();

}
