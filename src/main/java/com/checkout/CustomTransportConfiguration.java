package com.checkout;

import lombok.Builder;
import lombok.Setter;

@Builder
public class CustomTransportConfiguration implements TransportConfiguration {

    @Setter
    private int defaultHttpStatusCode;

    @Override
    public int getDefaultHttpStatusCode() {
        return defaultHttpStatusCode;
    }
}
