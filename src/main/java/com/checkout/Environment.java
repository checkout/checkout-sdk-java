package com.checkout;

import org.apache.commons.lang3.EnumUtils;

public enum Environment {

    SANDBOX("https://api.sandbox.checkout.com/", "https://files.sandbox.checkout.com/", "https://access.sandbox.checkout.com/connect/token"),
    PRODUCTION("https://api.checkout.com/", "https://files.checkout.com/", "https://access.checkout.com/connect/token");

    private final String uri;
    private final String filesApiURI;
    private final String oauthAuthorizeURI;

    Environment(final String uri,
                final String filesApiURI,
                final String oauthAuthorizeURI) {
        this.uri = uri;
        this.filesApiURI = filesApiURI;
        this.oauthAuthorizeURI = oauthAuthorizeURI;
    }

    public String getUri() {
        return uri;
    }

    public String getFilesApiURI() {
        return filesApiURI;
    }

    public String getOauthAuthorizeURI() {
        return oauthAuthorizeURI;
    }

    public static Environment fromString(final String environment) {
        if (EnumUtils.isValidEnum(Environment.class, environment)) {
            return Environment.valueOf(environment.toUpperCase());
        } else {
            return null;
        }
    }

    /**
     * @deprecated Will be removed in a future version
     */
    @Deprecated
    public static Environment lookup(final boolean useSandbox) {
        return useSandbox ? SANDBOX : PRODUCTION;
    }

}
