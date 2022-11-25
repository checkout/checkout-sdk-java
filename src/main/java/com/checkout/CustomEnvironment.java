package com.checkout;

import lombok.Builder;
import lombok.Data;

import java.net.URI;

@Data
@Builder
public class CustomEnvironment implements IEnvironment {

    private URI checkoutApi;

    private URI filesApi;

    private URI transfersApi;

    private URI balancesApi;

    private URI oAuthAuthorizationApi;

    private boolean sandbox;
}
