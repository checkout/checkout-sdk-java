package com.checkout;

import static com.checkout.common.CheckoutUtils.validateParams;

import java.nio.charset.StandardCharsets;

public abstract class AbstractClient {

    private static final char[] HEX = "0123456789ABCDEF".toCharArray();
    private static final String INVALID_PATH_PARAMETER =
            "Invalid path parameter: it must be valid text, not null, empty, '.' or '..'";

    protected final ApiClient apiClient;
    protected final CheckoutConfiguration configuration;
    private final SdkAuthorizationType authorizationType;

    protected AbstractClient(final ApiClient apiClient,
                             final CheckoutConfiguration configuration,
                             final SdkAuthorizationType authorizationType) {
        validateParams("apiClient", apiClient, "configuration", configuration, "authorizationType", authorizationType);
        this.apiClient = apiClient;
        this.configuration = configuration;
        this.authorizationType = authorizationType;
    }

    protected SdkAuthorization sdkAuthorization(final SdkAuthorizationType authorizationType) {
        return configuration.getSdkCredentials().getAuthorization(authorizationType);
    }

    protected SdkAuthorization sdkAuthorization() {
        return configuration.getSdkCredentials().getAuthorization(authorizationType);
    }

    /**
     * Joins the parameters into a request path, percent-encoding each one as exactly one segment. A
     * prefix of several segments must be passed as separate parameters.
     *
     * @param pathParams the path segments, in order
     * @return the request path, relative to the base URI
     * @throws CheckoutArgumentException if a parameter is null, empty, "." or "..", or is not valid
     *                                   UTF-16 text (an unpaired surrogate)
     */
    protected static String buildPath(final String... pathParams) {
        final StringBuilder path = new StringBuilder();
        for (final String pathParam : pathParams) {
            if (path.length() > 0) {
                path.append('/');
            }
            path.append(encodePathSegment(pathParam));
        }
        return path.toString();
    }

    /**
     * Percent-encodes a path parameter as UTF-8, keeping only the RFC 3986 unreserved characters
     * (letters, digits, "-", ".", "_" and "~"), so the result is exactly one path segment.
     */
    private static String encodePathSegment(final String pathParam) {
        if (pathParam == null || pathParam.isEmpty() || ".".equals(pathParam) || "..".equals(pathParam)
                || hasUnpairedSurrogate(pathParam)) {
            throw new CheckoutArgumentException(INVALID_PATH_PARAMETER);
        }
        final StringBuilder encoded = new StringBuilder(pathParam.length());
        for (final byte b : pathParam.getBytes(StandardCharsets.UTF_8)) {
            final int c = b & 0xFF;
            if (isUnreserved(c)) {
                encoded.append((char) c);
            } else {
                encoded.append('%').append(HEX[c >> 4]).append(HEX[c & 0x0F]);
            }
        }
        return encoded.toString();
    }

    private static boolean isUnreserved(final int c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')
                || c == '-' || c == '.' || c == '_' || c == '~';
    }

    private static boolean hasUnpairedSurrogate(final String value) {
        for (int i = 0; i < value.length(); i++) {
            final char c = value.charAt(i);
            if (Character.isHighSurrogate(c) && i + 1 < value.length() && Character.isLowSurrogate(value.charAt(i + 1))) {
                i++;
            } else if (Character.isSurrogate(c)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isSandbox() {
        return configuration.getEnvironment().isSandbox();
    }

}
