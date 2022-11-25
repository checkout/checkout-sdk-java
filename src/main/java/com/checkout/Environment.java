package com.checkout;

import lombok.Getter;

import java.net.URI;

import static java.net.URI.create;

@Getter
public enum Environment implements IEnvironment {

    SANDBOX(create("https://api.sandbox.checkout.com/"),
            create("https://files.sandbox.checkout.com/"),
            create("https://transfers.sandbox.checkout.com/"),
            create("https://balances.sandbox.checkout.com/"),
            create("https://access.sandbox.checkout.com/connect/token"),
            true),
    PRODUCTION(create("https://api.checkout.com/"),
            create("https://files.checkout.com/"),
            create("https://transfers.checkout.com/"),
            create("https://balances.checkout.com/"),
            create("https://access.checkout.com/connect/token"),
            false);

    private final URI checkoutApi;
    private final URI filesApi;
    private final URI transfersApi;
    private final URI balancesApi;
    private final URI oAuthAuthorizationApi;
    private final boolean sandbox;

    Environment(final URI checkoutApi,
                final URI filesApi,
                final URI transfersApi,
                final URI balancesApi,
                final URI oAuthAuthorizationApi,
                final boolean sandbox) {
        this.checkoutApi = checkoutApi;
        this.filesApi = filesApi;
        this.transfersApi = transfersApi;
        this.balancesApi = balancesApi;
        this.oAuthAuthorizationApi = oAuthAuthorizationApi;
        this.sandbox = sandbox;
    }
}
