package com.checkout;

import java.net.URI;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * The base URIs of an environment moved to the merchant-specific subdomain: the subdomain is
 * prepended to the host of {@code checkoutApi} and {@code oAuthAuthorizationApi}.
 */
public final class EnvironmentSubdomain {

    private static final Pattern SUBDOMAIN_PATTERN = Pattern.compile("^(?:pl-)?[a-z0-9]+$");

    private URI checkoutApi;
    private URI oAuthAuthorizationApi;

    /**
     * Moves the environment's base URIs to the merchant-specific subdomain. {@code checkoutApi} is
     * validated and normalised first with the same rules as every base URI; the rebuilt URIs keep the port,
     * path, query and fragment of that URI (normalised for {@code checkoutApi}, as configured for
     * {@code oAuthAuthorizationApi}) without decoding, and leave out any user information.
     *
     * @param environment the environment whose {@code checkoutApi} and {@code oAuthAuthorizationApi} are moved
     * @param subdomain   the merchant-specific subdomain, typically the client ID without the "cli_" prefix
     * @throws CheckoutArgumentException if the subdomain is invalid, a URI is missing or invalid, or its host
     *                                   cannot take a subdomain (empty, an IPv6 literal or an IPv4 address);
     *                                   the error names the setting, never the value
     */
    public EnvironmentSubdomain(IEnvironment environment, String subdomain) {
        validateSubdomain(subdomain);
        checkoutApi = BaseUri.withSubdomain(BaseUri.normalise(environment.getCheckoutApi(), "checkoutApi"), subdomain, "checkoutApi");
        oAuthAuthorizationApi = BaseUri.withSubdomain(environment.getOAuthAuthorizationApi(), subdomain, "oAuthAuthorizationApi");
    }

    /**
     * @return the {@code checkoutApi} base URI on the subdomain host
     */
    public URI getCheckoutApi() {
        return checkoutApi;
    }

    /**
     * @return the {@code oAuthAuthorizationApi} URI on the subdomain host
     */
    public URI getOAuthAuthorizationApi() {
        return oAuthAuthorizationApi;
    }

    private static void validateSubdomain(final String subdomain) {
        final Matcher matcher = subdomain == null ? null : SUBDOMAIN_PATTERN.matcher(subdomain);
        if (matcher == null || !matcher.matches()) {
            throw new CheckoutArgumentException("invalid environment subdomain - provide your merchant-specific subdomain, typically your client ID excluding the cli_ prefix (see https://api-reference.checkout.com/#section/Base-URLs)");
        }
    }

}
