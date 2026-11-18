package com.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UriCharactersTest {

    @ParameterizedTest
    @ValueSource(strings = {"pay_123", "a", "%41", "%2e%2E", "A-Z_a.z~0-9", "%ff%00x", "%AF%af"})
    void shouldAcceptEncodedSegments(final String segment) {
        assertTrue(UriCharacters.isPercentEncoded(segment, ""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "%", "%4", "%4G", "%G4", "%@0", "a%", "a%4", "%%41", "a b", "a+b", "a*b", "a=b", "a&b",
            "a;b", "a:b", "a@b", "a[b", "a`b", "a{b", "a/b", "a\\b", "a?b", "a#b", "café", "١", "Ａ",
            "😀", "\ud800", "a\u0000b"})
    void shouldRefuseSegmentsThatAreNotEncoded(final String segment) {
        assertFalse(UriCharacters.isPercentEncoded(segment, ""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a=1", "a=1&b=2", "q=%20x", "a*b+c", "&&", "="})
    void shouldAcceptEncodedQueries(final String query) {
        assertTrue(UriCharacters.isPercentEncoded(query, UriCharacters.QUERY_EXTRA));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a=1 b", "a=%2", "a=1;b", "a=1#f", "a=?", "a=/b", "a=café", "a=@", "a=:"})
    void shouldRefuseQueriesThatAreNotEncoded(final String query) {
        assertFalse(UriCharacters.isPercentEncoded(query, UriCharacters.QUERY_EXTRA));
    }

    @Test
    void shouldTreatOnlyUnreservedCharactersAsUnreserved() {
        for (int c = 0; c < 256; c++) {
            final boolean expected = (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')
                    || "-._~".indexOf(c) >= 0;
            assertEquals(expected, UriCharacters.isUnreserved(c), "character " + c);
        }
    }

    /**
     * The validator against the grammar it implements (RFC 3986 terms), written here as a regular
     * expression used only as the reference:
     * segment = 1*( ALPHA / DIGIT / "-" / "." / "_" / "~" / pct-encoded ), and
     * query = 1*( ALPHA / DIGIT / "-" / "." / "_" / "~" / "*" / "+" / "=" / "&amp;" / pct-encoded ), where
     * pct-encoded = "%" HEXDIG HEXDIG. Inputs: every char alone, after "%" and before a hex digit, and every
     * pair of ASCII chars alone, after "%" and between letters, so the range boundaries ("/", ":", "@",
     * "[", "`", "{"), the hex boundaries and surrogates are all covered.
     */
    @Test
    void shouldMatchTheSegmentAndQueryGrammarOnEveryShortInput() {
        final Pattern segmentGrammar = Pattern.compile("(?:[A-Za-z0-9._~-]|%[0-9A-Fa-f]{2})+"); // NOSONAR reference only
        final Pattern queryGrammar = Pattern.compile("(?:[A-Za-z0-9._~*+=&-]|%[0-9A-Fa-f]{2})+"); // NOSONAR reference only
        final List<String> inputs = new ArrayList<>();
        for (int c = 0; c <= Character.MAX_VALUE; c++) {
            inputs.add(String.valueOf((char) c));
            inputs.add("%" + (char) c + "0");
            inputs.add("%0" + (char) c);
        }
        for (char x = 0; x < 128; x++) {
            for (char y = 0; y < 128; y++) {
                inputs.add("" + x + y);
                inputs.add("%" + x + y);
                inputs.add("a%" + x + y + "b");
            }
        }
        for (final String candidate : inputs) {
            assertEquals(segmentGrammar.matcher(candidate).matches(),
                    UriCharacters.isPercentEncoded(candidate, ""), () -> "segment: " + escape(candidate));
            assertEquals(queryGrammar.matcher(candidate).matches(),
                    UriCharacters.isPercentEncoded(candidate, UriCharacters.QUERY_EXTRA), () -> "query: " + escape(candidate));
        }
    }

    private static String escape(final String value) {
        final StringBuilder builder = new StringBuilder();
        for (final char c : value.toCharArray()) {
            builder.append(c >= 0x20 && c < 0x7f ? String.valueOf(c) : String.format("\\u%04x", (int) c));
        }
        return builder.toString();
    }
}
