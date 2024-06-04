package com.checkout;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EnvironmentSubdomain {

    private URI checkoutApi;

    public EnvironmentSubdomain(IEnvironment environment, String subdomain) {
        checkoutApi = addSubdomainToApiUrlEnvironment(environment, subdomain);
    }

    public URI getCheckoutApi() {
        return checkoutApi;
    }

    private static URI addSubdomainToApiUrlEnvironment(IEnvironment environment, String subdomain) {
        URI apiUrl = environment.getCheckoutApi();
        URI newEnvironment = null;
        try {
            newEnvironment = new URI(apiUrl.toString());
        } catch (final URISyntaxException e) {
            throw new CheckoutException(e);
        }
        Pattern pattern = Pattern.compile("^[0-9a-z]{8,11}$");
        Matcher matcher = pattern.matcher(subdomain);
        if (matcher.matches()) {
            String host = apiUrl.getHost();
            String scheme = apiUrl.getScheme();
            int port = apiUrl.getPort();
            String newHost = subdomain + "." + host;
            try {
                newEnvironment = new URI(scheme, null, newHost, port, apiUrl.getPath(), apiUrl.getQuery(), apiUrl.getFragment());
            } catch (final URISyntaxException e) {
                throw new CheckoutException(e);
            }
        }
        return newEnvironment;
    }

}
