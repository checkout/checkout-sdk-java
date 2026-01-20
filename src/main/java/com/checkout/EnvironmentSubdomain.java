package com.checkout;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EnvironmentSubdomain {

    private URI checkoutApi;
    private URI oAuthAuthorizationApi;

    public EnvironmentSubdomain(IEnvironment environment, String subdomain) {
        checkoutApi = createUrlWithSubdomain(environment.getCheckoutApi(), subdomain);
        oAuthAuthorizationApi = createUrlWithSubdomain(environment.getOAuthAuthorizationApi(), subdomain);
    }

    public URI getCheckoutApi() {
        return checkoutApi;
    }

    public URI getOAuthAuthorizationApi() {
        return oAuthAuthorizationApi;
    }

    /**
     * Applies subdomain transformation to any given URI.
     * If the subdomain is valid (alphanumeric pattern), prepends it to the host.
     * Otherwise, returns the original URI unchanged.
     *
     * @param originalUrl the original URI to transform
     * @param subdomain the subdomain to prepend
     * @return the transformed URI with subdomain, or original URI if subdomain is invalid
     */
    private static URI createUrlWithSubdomain(URI originalUrl, String subdomain) {
        URI newEnvironment = null;
        try {
            newEnvironment = new URI(originalUrl.toString());
        } catch (final URISyntaxException e) {
            throw new CheckoutException(e);
        }
        
        Pattern pattern = Pattern.compile("^[0-9a-z]+$");
        Matcher matcher = pattern.matcher(subdomain);
        if (matcher.matches()) {
            String host = originalUrl.getHost();
            String scheme = originalUrl.getScheme();
            int port = originalUrl.getPort();
            String newHost = subdomain + "." + host;
            try {
                newEnvironment = new URI(scheme, null, newHost, port, originalUrl.getPath(), originalUrl.getQuery(), originalUrl.getFragment());
            } catch (final URISyntaxException e) {
                throw new CheckoutException(e);
            }
        }
        return newEnvironment;
    }

}
