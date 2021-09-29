package com.checkout;

public enum Environment {

    SANDBOX("https://api.sandbox.checkout.com/", "https://files.sandbox.checkout.com/"),
    PRODUCTION("https://api.checkout.com/", "https://files.checkout.com/");

    private final String uri;
    private final String filesApiURI;

    Environment(final String uri, final String filesApiURI) {
        this.uri = uri;
        this.filesApiURI = filesApiURI;
    }

    public String getUri() {
        return uri;
    }

    public String getFilesApiURI() {
        return filesApiURI;
    }

    /**
     * @deprecated Will be removed in a future version
     */
    @Deprecated
    public static Environment lookup(final boolean useSandbox) {
        return useSandbox ? SANDBOX : PRODUCTION;
    }

}
