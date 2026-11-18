package com.checkout;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Validation of configured base URIs and the environment subdomain rebuild, shared by the transport
 * and {@link EnvironmentSubdomain}. Errors name the setting ("filesApi"), never the value.
 */
final class BaseUri {

    private static final String INVALID_BASE_URI = "Invalid base URI for %s: it must be an absolute http or https URI "
            + "with an ASCII host written without percent-encoding, an optional port from 1 to 65535, no user information, "
            + "and a path without dot segments, encoded separators or encoded control characters";
    private static final String INVALID_SUBDOMAIN_URI = "Invalid URI for %s: the environment subdomain can only be "
            + "added to an absolute URI whose host is a domain name of ASCII letters, digits, \"-\" and \"_\", "
            + "written without percent-encoding, not an IP address, with an optional port from 1 to 65535";
    private static final int MAX_PORT = 65535;

    private BaseUri() {
    }

    /**
     * Checks a base URI and returns it with a normalised path. It must be absolute http or https with a
     * non-empty host, an optional port from 1 to 65535 and no user information, and its authority must
     * be visible ASCII and hold no "%" (an escape there reads differently once decoded). After normalising, no path
     * segment may be "." or ".." (also when percent-encoded, and also before a ";" parameter) or hold an
     * encoded "/", {@code \} or ASCII control character. A base URI with no path gets the root path "/".
     * Request paths are resolved against the base directory, the path up to its last "/".
     *
     * @param baseUri the configured base URI
     * @param setting the name of the setting it comes from, used in errors instead of the value
     * @return the normalised base URI
     * @throws CheckoutArgumentException if the base URI is missing or invalid
     */
    static URI normalise(final URI baseUri, final String setting) {
        if (baseUri == null) {
            throw new CheckoutArgumentException(setting + " cannot be null");
        }
        final String scheme = baseUri.getScheme();
        final String authority = baseUri.getRawAuthority();
        if (scheme == null || baseUri.isOpaque() || authority == null || authority.indexOf('@') >= 0
                || authority.indexOf('%') >= 0 || !isVisibleAscii(authority) || hostOf(authority) == null
                || !("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme))) {
            throw invalid(INVALID_BASE_URI, setting);
        }
        final String path = baseUri.getRawPath();
        final URI normalised = path == null || path.isEmpty() ? baseUri.resolve("/") : baseUri.normalize();
        for (final String segment : normalised.getRawPath().split("/", -1)) {
            final int parameters = segment.indexOf(';');
            if (UriCharacters.isDotSegment(parameters >= 0 ? segment.substring(0, parameters) : segment)
                    || UriCharacters.hasEncodedSeparatorOrControl(segment)) {
                throw invalid(INVALID_BASE_URI, setting);
            }
        }
        return normalised;
    }

    /**
     * Prepends the subdomain to the host of the URI, refusing an authority that holds a "%" and a host
     * that is not a domain name (see {@link #isDomainName(String)}). Port, raw path, raw query and raw
     * fragment of the given URI are copied without decoding (callers pass {@code checkoutApi} already
     * normalised); user information is not copied, as before (base URIs refuse it in
     * {@link #normalise(URI, String)} before they get here).
     *
     * @param uri       the configured URI
     * @param subdomain the subdomain, already validated
     * @param setting   the name of the setting the URI comes from, used in errors instead of the value
     * @return the URI on the subdomain host
     * @throws CheckoutArgumentException if the URI is missing, not absolute with an authority, its
     *                                   authority holds a "%", its port is out of range, or its host is not a
     *                                   domain name
     */
    static URI withSubdomain(final URI uri, final String subdomain, final String setting) {
        if (uri == null) {
            throw new CheckoutArgumentException(setting + " cannot be null");
        }
        final String authority = uri.getRawAuthority();
        if (uri.getScheme() == null || uri.isOpaque() || authority == null || authority.indexOf('%') >= 0) {
            throw invalid(INVALID_SUBDOMAIN_URI, setting);
        }
        final int hostStart = authority.lastIndexOf('@') + 1;
        final String host = hostOf(authority.substring(hostStart));
        if (host == null || !isDomainName(host)) {
            throw invalid(INVALID_SUBDOMAIN_URI, setting);
        }
        final StringBuilder rebuilt = new StringBuilder()
                .append(uri.getScheme()).append("://")
                .append(subdomain).append('.')
                .append(authority.substring(hostStart));
        if (uri.getRawPath() != null) {
            rebuilt.append(uri.getRawPath());
        }
        if (uri.getRawQuery() != null) {
            rebuilt.append('?').append(uri.getRawQuery());
        }
        if (uri.getRawFragment() != null) {
            rebuilt.append('#').append(uri.getRawFragment());
        }
        try {
            return new URI(rebuilt.toString());
        } catch (final URISyntaxException e) {
            throw invalid(INVALID_SUBDOMAIN_URI, setting);
        }
    }

    /**
     * Reads the host from a raw authority without user information, because {@link URI#getHost()} is
     * null for host names that are not valid DNS names, such as those with "_".
     *
     * @return the host (an IPv6 literal keeps its brackets), or null when the host is empty or the port
     *         is not a number from 1 to 65535 (leading zeros allowed); an empty port after ":" is allowed
     */
    private static String hostOf(final String hostAndPort) {
        final int hostEnd = hostAndPort.startsWith("[") ? hostAndPort.indexOf(']') + 1 : hostAndPort.indexOf(':');
        final String host = hostEnd < 0 ? hostAndPort : hostAndPort.substring(0, hostEnd);
        final String port = hostEnd < 0 ? "" : hostAndPort.substring(hostEnd);
        if (host.isEmpty() || (!port.isEmpty() && port.charAt(0) != ':')) {
            return null;
        }
        int value = 0;
        for (int i = 1; i < port.length(); i++) {
            final char c = port.charAt(i);
            if (c < '0' || c > '9') {
                return null;
            }
            value = value * 10 + (c - '0');
            if (value > MAX_PORT) {
                return null;
            }
        }
        return port.length() > 1 && value == 0 ? null : host;
    }

    /**
     * True when the host can take a subdomain: not an IPv6 literal, every label (ignoring one trailing
     * ".") non-empty, made of ASCII letters, digits, "-" and "_", and neither starting nor ending with
     * "-", and the last label starting with an ASCII letter, which leaves out IPv4 addresses in every
     * notation ("127.0.0.1", "127.1", "2130706433", "0x7f000001", "017700000001").
     */
    private static boolean isDomainName(final String host) {
        final String name = host.endsWith(".") ? host.substring(0, host.length() - 1) : host;
        final String[] labels = name.split("\\.", -1);
        for (final String label : labels) {
            if (label.isEmpty() || label.charAt(0) == '-' || label.charAt(label.length() - 1) == '-') {
                return false;
            }
            for (int i = 0; i < label.length(); i++) {
                final char c = label.charAt(i);
                if (!isAsciiLetter(c) && !(c >= '0' && c <= '9') && c != '-' && c != '_') {
                    return false;
                }
            }
        }
        return isAsciiLetter(labels[labels.length - 1].charAt(0));
    }

    private static boolean isAsciiLetter(final char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }

    private static boolean isVisibleAscii(final String value) {
        for (int i = 0; i < value.length(); i++) {
            final char c = value.charAt(i);
            if (c <= ' ' || c > '~') {
                return false;
            }
        }
        return true;
    }

    private static CheckoutArgumentException invalid(final String message, final String setting) {
        return new CheckoutArgumentException(String.format(message, setting));
    }
}
