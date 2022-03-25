package com.checkout;

import java.net.URI;

import static java.net.URI.create;

public enum Environment {

    SANDBOX(create("https://api.sandbox.checkout.com/"),
            create("https://files.sandbox.checkout.com/"),
            create("https://transfers.sandbox.checkout.com/"),
            create("https://balances.sandbox.checkout.com/"),
            create("https://access.sandbox.checkout.com/connect/token")),
    PRODUCTION(create("https://api.checkout.com/"),
            create("https://files.checkout.com/"),
            create("https://transfers.checkout.com/"),
            create("https://balances.checkout.com/"),
            create("https://access.checkout.com/connect/token"));

    private final URI uri;
    private final URI filesApiURI;
    private final URI transfersApiURI;
    private final URI balancesApiURI;
    private final URI oauthAuthorizeURI;

    Environment(final URI uri,
                final URI filesApiURI,
                final URI transfersApiURI,
                final URI balancesApiURI,
                final URI oauthAuthorizeURI) {
        this.uri = uri;
        this.filesApiURI = filesApiURI;
        this.transfersApiURI = transfersApiURI;
        this.balancesApiURI = balancesApiURI;
        this.oauthAuthorizeURI = oauthAuthorizeURI;
    }

    public URI getUri() {
        return uri;
    }

    public URI getFilesApiUri() {
        return filesApiURI;
    }

    public URI getTransfersApiURI() {
        return transfersApiURI;
    }

    public URI getBalancesApiURI() {
        return balancesApiURI;
    }

    public URI getOAuthAuthorizeUri() {
        return oauthAuthorizeURI;
    }

}
