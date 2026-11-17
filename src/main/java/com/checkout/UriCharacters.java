package com.checkout;

import java.util.regex.Pattern;

/**
 * Character rules shared by the path builder and the transport, so both read the same grammar.
 */
final class UriCharacters {

    /** Characters a query string may hold unencoded besides the unreserved ones. */
    static final String QUERY_EXTRA = "*+=&";

    private static final Pattern ENCODED_SEPARATOR_OR_CONTROL =
            Pattern.compile("%(?:2F|5C|[01][0-9A-F]|7F)", Pattern.CASE_INSENSITIVE);

    private UriCharacters() {
    }

    /**
     * True for the RFC 3986 unreserved characters: ASCII letters and digits, "-", ".", "_" and "~".
     *
     * @param c a character or a byte value from 0 to 255
     * @return whether it may appear unencoded in a path segment
     */
    static boolean isUnreserved(final int c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')
                || c == '-' || c == '.' || c == '_' || c == '~';
    }

    /**
     * True when the value is not empty and every character is unreserved, one of {@code extra}, or a
     * "%" followed by two hex digits. One pass, no backtracking.
     *
     * @param value the text to check
     * @param extra characters allowed besides the unreserved ones ("" for a path segment,
     *              {@link #QUERY_EXTRA} for a query string)
     * @return whether the value is already percent-encoded under that grammar
     */
    static boolean isPercentEncoded(final String value, final String extra) {
        if (value.isEmpty()) {
            return false;
        }
        int i = 0;
        while (i < value.length()) {
            final char c = value.charAt(i);
            if (c == '%') {
                if (i + 2 >= value.length() || !isHexDigit(value.charAt(i + 1)) || !isHexDigit(value.charAt(i + 2))) {
                    return false;
                }
                i += 3;
            } else if (isUnreserved(c) || extra.indexOf(c) >= 0) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * True for "." and "..", also when one or both dots are percent-encoded ("%2e" or "%2E").
     *
     * @param segment one path segment, without its ";" parameters
     * @return whether it is a dot segment
     */
    static boolean isDotSegment(final String segment) {
        final String name = segment.replace("%2e", ".").replace("%2E", ".");
        return ".".equals(name) || "..".equals(name);
    }

    /**
     * True when the value holds an encoded "/", {@code \} or ASCII control character ("%2F", "%5C",
     * "%00" to "%1F", "%7F", in any case).
     *
     * @param value the raw text to check
     * @return whether it holds one of those escapes
     */
    static boolean hasEncodedSeparatorOrControl(final String value) {
        return ENCODED_SEPARATOR_OR_CONTROL.matcher(value).find();
    }

    private static boolean isHexDigit(final char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }
}
