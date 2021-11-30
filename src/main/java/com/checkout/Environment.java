package com.checkout;

import java.net.URI;

import static java.net.URI.create;

public enum Environment {

    SANDBOX(create("https://api.sandbox.checkout.com/"), create("https://files.sandbox.checkout.com/"), create("https://access.sandbox.checkout.com/connect/token")),
    PRODUCTION(create("https://api.checkout.com/"), create("https://files.checkout.com/"), create("https://access.checkout.com/connect/token"));

    private final URI uri;
    private final URI filesApiURI;
    private final URI oauthAuthorizeURI;

    Environment(final URI uri,
                final URI filesApiURI,
                final URI oauthAuthorizeURI) {
        this.uri = uri;
        this.filesApiURI = filesApiURI;
        this.oauthAuthorizeURI = oauthAuthorizeURI;
    }

    public URI getUri() {
        return uri;
    }

    public URI getFilesApiUri() {
        return filesApiURI;
    }

    public URI getOAuthAuthorizeUri() {
        return oauthAuthorizeURI;
    }

}
