package com.checkout;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomTransportConfiguration implements TransportConfiguration {

    private int defaultHttpStatusCode;
}
