package com.checkout;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public final class EnvironmentSubdomain {

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
     * Applies subdomain transformation to any given URI, prepending the subdomain to the host.
     *
     * @param originalUrl the original URI to transform
     * @param subdomain the subdomain to prepend
     * @return the transformed URI with subdomain
     * @throws CheckoutArgumentException if the subdomain is not a valid merchant-specific subdomain
     */
    private static URI createUrlWithSubdomain(URI originalUrl, String subdomain) {
        Pattern pattern = Pattern.compile("^(?:pl-)?[a-z0-9]+$");
        Matcher matcher = subdomain == null ? null : pattern.matcher(subdomain);
        if (matcher == null || !matcher.matches()) {
            throw new CheckoutArgumentException("invalid environment subdomain - provide your merchant-specific subdomain, the first 8 characters of your client ID (see https://api-reference.checkout.com/#section/Base-URLs)");
        }

        String host = originalUrl.getHost();
        String scheme = originalUrl.getScheme();
        int port = originalUrl.getPort();
        String newHost = subdomain + "." + host;
        try {
            return new URI(scheme, null, newHost, port, originalUrl.getPath(), originalUrl.getQuery(), originalUrl.getFragment());
        } catch (final URISyntaxException e) {
            throw new CheckoutException(e);
        }
    }

}
