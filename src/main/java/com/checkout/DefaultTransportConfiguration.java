package com.checkout;

import org.apache.http.HttpStatus;

public class DefaultTransportConfiguration implements TransportConfiguration {

    private final int defaultHttpStatusCode;

    public DefaultTransportConfiguration() {
        this.defaultHttpStatusCode = HttpStatus.SC_BAD_REQUEST;
    }

    @Override
    public int getDefaultHttpStatusCode() {
        return defaultHttpStatusCode;
    }
}
