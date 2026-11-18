package com.checkout;

import static com.checkout.TestHelper.VALID_DEFAULT_SK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Base URI handling through the public SDK builder, with an HTTP client that records each request
 * instead of sending it ({@link RecordingHttpClientBuilder}).
 */
class CheckoutSdkBaseUriTest {

    private static final URI VALID = URI.create("https://proxy.example/");
    private static final String[] SETTINGS = {"checkoutApi", "filesApi", "transfersApi", "balancesApi", "forwardApi", "identityApi"};

    private final RecordingHttpClientBuilder httpClientBuilder = new RecordingHttpClientBuilder();

    private CheckoutApi build(final CustomEnvironment environment) {
        return CheckoutSdk.builder().staticKeys()
                .secretKey(VALID_DEFAULT_SK)
                .environment(environment)
                .useLegacyDomain()
                .httpClientBuilder(httpClientBuilder)
                .recordTelemetry(false)
                .build();
    }

    private static CustomEnvironment environment(final String setting, final URI value) {
        return CustomEnvironment.builder()
                .checkoutApi("checkoutApi".equals(setting) ? value : VALID)
                .filesApi("filesApi".equals(setting) ? value : VALID)
                .transfersApi("transfersApi".equals(setting) ? value : VALID)
                .balancesApi("balancesApi".equals(setting) ? value : VALID)
                .forwardApi("forwardApi".equals(setting) ? value : VALID)
                .identityApi("identityApi".equals(setting) ? value : VALID)
                .oAuthAuthorizationApi(URI.create("https://access.proxy.example/connect/token"))
                .sandbox(true)
                .build();
    }

    private CheckoutApi buildWithSubdomain(final CustomEnvironment environment) {
        return CheckoutSdk.builder().staticKeys()
                .secretKey(VALID_DEFAULT_SK)
                .environment(environment)
                .environmentSubdomain("abc123")
                .httpClientBuilder(httpClientBuilder)
                .recordTelemetry(false)
                .build();
    }

    private String requestUrlWithSubdomainFor(final String baseUri) {
        buildWithSubdomain(environment("checkoutApi", URI.create(baseUri))).customersClient().getSync("cus_123");
        assertEquals(1, httpClientBuilder.requests.size());
        return httpClientBuilder.requests.get(0);
    }

    private String requestUrlFor(final String baseUri) {
        build(environment("checkoutApi", URI.create(baseUri))).customersClient().getSync("cus_123");
        assertEquals(1, httpClientBuilder.requests.size());
        return httpClientBuilder.requests.get(0);
    }

    private static Stream<Arguments> invalidBaseUrisPerSetting() {
        final String[] invalid = {
                "localhost:9000/", "api.example.com/", "/checkout/", "mailto:x@example.com", "ftp://proxy.example/",
                "file:///tmp/checkout/", "https:///payments/", "http://:80/", "https://user@/", "https://@/",
                "https://proxy.example:abc/", "https://proxy.example:80:90/",
                "https://user:secret-value@proxy.example/", "https://user@proxy.example/", "https://%75ser@proxy.example/",
                "https://proxy.example/../x/", "https://proxy.example/%2e%2e/x/", "https://proxy.example/%2E/x/",
                "https://proxy.example/a/%2e%2E/", "https://proxy.example/./../x/",
                "https://proxy.example/..;/x/", "https://proxy.example/%2e%2e;x/y/", "https://proxy.example/.;a/",
                "https://proxy.example/a/..;/", "https://proxy.example/a%2Fb/", "https://proxy.example/a%2f..%2fb/",
                "https://proxy.example/a%5Cb/", "https://proxy.example/a%00b/", "https://proxy.example/a%0Ab/",
                "https://proxy.example/a%7Fb/",
                "http://good.example%40127.0.0.1:8080/v1/", "http://user%3Asecret-value%40127.0.0.1/",
                "http://127.0.0.1%3A8080/", "https://other.example%2Fgood.example/", "https://proxy%2Eexample/",
                "https://%70roxy.example/", "https://proxy.example:0/", "https://proxy.example:00000/",
                "https://proxy.example:65536/", "https://proxy.example:99999999999/",
                "https://b\u00FCcher.example/", "https://api.te\u202Est/", "https://\u0430pi.example/"
        };
        final List<Arguments> arguments = new ArrayList<>();
        for (final String setting : SETTINGS) {
            for (final String value : invalid) {
                arguments.add(Arguments.of(setting, value));
            }
        }
        return arguments.stream();
    }

    @ParameterizedTest
    @MethodSource("invalidBaseUrisPerSetting")
    void shouldRefuseInvalidBaseUrisNamingTheSettingButNotTheValue(final String setting, final String value) {
        final CheckoutArgumentException exception = assertThrows(CheckoutArgumentException.class,
                () -> build(environment(setting, URI.create(value))));

        final String message = exception.getMessage();
        assertTrue(message.contains(setting), () -> "the error names the setting: " + message);
        for (final String other : SETTINGS) {
            if (!other.equals(setting)) {
                assertFalse(message.contains(other + " "), () -> "the error names only the invalid setting: " + message);
            }
        }
        assertFalse(message.contains(value), () -> "the error does not echo the value: " + message);
        assertFalse(message.contains("secret-value"), () -> "the error does not echo user information: " + message);
        assertFalse(message.contains("proxy.example"), () -> "the error does not echo the host: " + message);
        assertTrue(httpClientBuilder.requests.isEmpty(), "nothing is sent");
    }

    @Test
    void shouldNameTheSettingWhenABaseUriIsMissing() {
        final CheckoutArgumentException exception = assertThrows(CheckoutArgumentException.class,
                () -> build(environment("filesApi", null)));
        assertTrue(exception.getMessage().contains("filesApi"), exception::getMessage);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://proxy.example/|https://proxy.example/customers/cus_123",
            "https://proxy.example|https://proxy.example/customers/cus_123",
            "https://proxy.example:8443|https://proxy.example:8443/customers/cus_123",
            "https://proxy.example:/|https://proxy.example/customers/cus_123",
            "https://proxy_host.example/|https://proxy_host.example/customers/cus_123",
            "http://checkout_proxy:8080/api/|http://checkout_proxy:8080/api/customers/cus_123",
            "https://[::1]:8443/api/|https://[::1]:8443/api/customers/cus_123",
            "http://127.1/|http://127.1/customers/cus_123",
            "http://localhost.example.net/|http://localhost.example.net/customers/cus_123",
            "https://proxy.example/checkout/|https://proxy.example/checkout/customers/cus_123",
            "https://proxy.example/a/../b/|https://proxy.example/b/customers/cus_123",
            "https://proxy.example/a/..|https://proxy.example/customers/cus_123",
            "https://proxy.example//|https://proxy.example/customers/cus_123",
            "https://proxy.example/...v1/|https://proxy.example/...v1/customers/cus_123",
            "https://proxy.example/v1;a=b/|https://proxy.example/v1;a=b/customers/cus_123",
            "https://proxy.example/...;x/|https://proxy.example/...;x/customers/cus_123",
            "https://proxy.example/a%20b/|https://proxy.example/a%20b/customers/cus_123",
            "https://proxy.example:1/|https://proxy.example:1/customers/cus_123",
            "https://proxy.example:65535/|https://proxy.example:65535/customers/cus_123",
            "https://proxy.example:08443/|https://proxy.example:8443/customers/cus_123"
    })
    void shouldSendRequestsUnderValidBaseUris(final String baseAndExpected) {
        final String[] parts = baseAndExpected.split("\\|");
        assertEquals(parts[1], requestUrlFor(parts[0]));
    }

    /** The base directory (the path up to the last "/") is kept, so without a trailing slash the last segment is replaced. */
    @Test
    void shouldReplaceTheLastBasePathSegmentWhenThereIsNoTrailingSlash() {
        assertEquals("https://proxy.example/customers/cus_123", requestUrlFor("https://proxy.example/checkout"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a/b@example.com", "a\\b@example.com", "..", "cus_1\r\nX: y"})
    void shouldRefuseCustomerIdentifiersThatAreNotOnePathSegment(final String identifier) {
        final CheckoutApi checkoutApi = build(environment("checkoutApi", VALID));

        assertThrows(CheckoutArgumentException.class, () -> checkoutApi.customersClient().getSync(identifier));
        assertTrue(httpClientBuilder.requests.isEmpty(), "nothing is sent");
    }

    @Test
    void shouldSendCustomerEmailsAsOnePathSegment() {
        build(environment("checkoutApi", VALID)).customersClient().getSync("a+b@example.com");

        assertEquals(Collections.singletonList("https://proxy.example/customers/a%2Bb%40example.com"), httpClientBuilder.requests);
    }

    private static Stream<String> invalidBaseUrisWithSubdomain() {
        return Stream.of(
                "https://user:secret-value@proxy.example/", "https://user@proxy.example/", "https://[::1]/",
                "https://[::1]:8443/api/", "http://127.0.0.1:8080/", "http://127.1/", "https://proxy.example/a/%2e%2e/b/",
                "https://proxy.example/a%2Fb/", "https://proxy.example/..;/x/", "https://proxy.example/../x/",
                "http://:80/", "https://@/", "localhost:9000/", "/checkout/", "ftp://proxy.example/",
                "https://u%3Ap%40good.example/", "http://0x7f000001/", "http://%31%32%37.0.0.1/", "http://2130706433/",
                "http://017700000001/", "http://proxy.0x7f/", "http://proxy.example.123/", "https://proxy.example:0/",
                "https://proxy.example:65536/", "https://api.te\u202Est/", "https://\u0430pi.example/",
                "https://.example.com/", "https://example..com/", "https://-x.example.com/", "https://x-.example.com/",
                "https://proxy.example../", "https://proxy.ex\u00E1mple/");
    }

    @ParameterizedTest
    @MethodSource("invalidBaseUrisWithSubdomain")
    void shouldRefuseInvalidBaseUrisWithASubdomainNamingTheSettingButNotTheValue(final String value) {
        final CheckoutArgumentException exception = assertThrows(CheckoutArgumentException.class,
                () -> buildWithSubdomain(environment("checkoutApi", URI.create(value))));

        final String message = exception.getMessage();
        assertTrue(message.contains("checkoutApi"), () -> "the error names the setting: " + message);
        assertFalse(message.contains(value), () -> "the error does not echo the value: " + message);
        assertFalse(message.contains("secret-value"), () -> "the error does not echo user information: " + message);
        assertFalse(message.contains("proxy.example") || message.contains("::1") || message.contains("127.")
                        || message.contains("good.example") || message.contains("0x7f"),
                () -> "the error does not echo the host: " + message);
        assertTrue(httpClientBuilder.requests.isEmpty(), "nothing is sent");
    }

    @Test
    void shouldNameTheSettingWhenTheCheckoutApiIsMissingWithASubdomain() {
        final CheckoutArgumentException exception = assertThrows(CheckoutArgumentException.class,
                () -> buildWithSubdomain(environment("checkoutApi", null)));
        assertTrue(exception.getMessage().contains("checkoutApi"), exception::getMessage);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://proxy.example/|https://abc123.proxy.example/customers/cus_123",
            "https://proxy.example|https://abc123.proxy.example/customers/cus_123",
            "https://my_host.example/|https://abc123.my_host.example/customers/cus_123",
            "http://checkout_proxy:8080/api/|http://abc123.checkout_proxy:8080/api/customers/cus_123",
            "https://proxy.example/a%20b/|https://abc123.proxy.example/a%20b/customers/cus_123",
            "https://proxy.example/a/../b/|https://abc123.proxy.example/b/customers/cus_123",
            "https://api.sandbox.checkout.com/|https://abc123.api.sandbox.checkout.com/customers/cus_123",
            "http://localhost:8080/|http://abc123.localhost:8080/customers/cus_123",
            "https://proxy.example.:8443/|https://abc123.proxy.example.:8443/customers/cus_123",
            "https://proxy.x0/|https://abc123.proxy.x0/customers/cus_123"
    })
    void shouldSendRequestsUnderTheSubdomainOfValidBaseUris(final String baseAndExpected) {
        final String[] parts = baseAndExpected.split("\\|");
        assertEquals(parts[1], requestUrlWithSubdomainFor(parts[0]));
    }

    @Test
    void shouldAddTheSubdomainToTheAuthorizationUriWithoutDecodingIt() {
        final CustomEnvironment environment = CustomEnvironment.builder()
                .checkoutApi(VALID)
                .oAuthAuthorizationApi(URI.create("https://my_host.example/a%2Fb/connect/token?x=%41"))
                .build();

        assertEquals("https://abc123.my_host.example/a%2Fb/connect/token?x=%41",
                new EnvironmentSubdomain(environment, "abc123").getOAuthAuthorizationApi().toString());
    }

    @Test
    void shouldLeaveUserInformationOutOfTheAuthorizationUriOnTheSubdomain() {
        final CustomEnvironment environment = CustomEnvironment.builder()
                .checkoutApi(VALID)
                .oAuthAuthorizationApi(URI.create("https://user:pass@access.example:8443/connect/token"))
                .build();

        assertEquals("https://abc123.access.example:8443/connect/token",
                new EnvironmentSubdomain(environment, "abc123").getOAuthAuthorizationApi().toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://[::1]/connect/token", "http://127.0.0.1/connect/token", "https:///connect/token",
            "connect/token", "mailto:x@example.com", "https://u%3Ap%40access.example/connect/token",
            "https://access%2Eexample/connect/token", "http://0x7f000001/connect/token",
            "https://access.example:0/connect/token", "https://access.example:65536/connect/token"})
    void shouldRefuseAuthorizationUrisThatCannotTakeASubdomainWithoutEchoingThem(final String value) {
        final CustomEnvironment environment = CustomEnvironment.builder()
                .checkoutApi(VALID)
                .oAuthAuthorizationApi(URI.create(value))
                .build();

        final CheckoutArgumentException exception = assertThrows(CheckoutArgumentException.class,
                () -> new EnvironmentSubdomain(environment, "abc123"));
        assertTrue(exception.getMessage().contains("oAuthAuthorizationApi"), exception::getMessage);
        assertFalse(exception.getMessage().contains(value), exception::getMessage);
    }

    @Test
    void shouldNameTheSettingWhenTheAuthorizationUriIsMissingWithASubdomain() {
        final CustomEnvironment environment = CustomEnvironment.builder().checkoutApi(VALID).build();

        final CheckoutArgumentException exception = assertThrows(CheckoutArgumentException.class,
                () -> new EnvironmentSubdomain(environment, "abc123"));
        assertTrue(exception.getMessage().contains("oAuthAuthorizationApi"), exception::getMessage);
    }
}
