package com.checkout;

import static com.checkout.ApacheHttpClientTransport.QUERY_CHARACTERS;
import static com.checkout.ApacheHttpClientTransport.SEGMENT_CHARACTERS;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.checkout.common.FilePurpose;
import com.checkout.common.FileRequest;
import com.sun.net.httpserver.HttpServer;

class ApacheHttpClientTransportRequestUrlTest {

    private static final URI SANDBOX = URI.create("https://api.sandbox.checkout.com/");
    private static final String VALID_PATH = "payments/pay_123";
    private static final String DOT_SEGMENT_PATH = "payments/../workflows/wf_1";

    private final SdkAuthorization authorization = new SdkAuthorization(PlatformType.DEFAULT, "sk_test");
    private final List<String> receivedPaths = Collections.synchronizedList(new ArrayList<>());
    private final List<String> receivedQueries = Collections.synchronizedList(new ArrayList<>());
    private final List<ExecutorService> executors = new ArrayList<>();

    private HttpServer server;

    @TempDir
    File tempDir;

    @BeforeEach
    void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            receivedPaths.add(exchange.getRequestURI().getRawPath());
            receivedQueries.add(String.valueOf(exchange.getRequestURI().getRawQuery()));
            final byte[] body = "{}".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(body);
            }
        });
        server.start();
    }

    @AfterEach
    void stopServerAndExecutors() {
        server.stop(0);
        executors.forEach(ExecutorService::shutdownNow);
    }

    private ApacheHttpClientTransport transport(final URI baseUri) {
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        executors.add(executor);
        return new ApacheHttpClientTransport(baseUri, HttpClientBuilder.create(), executor,
                new DefaultTransportConfiguration(), mock(CheckoutConfiguration.class));
    }

    private ApacheHttpClientTransport localTransport() {
        return transport(URI.create("http://127.0.0.1:" + server.getAddress().getPort() + "/"));
    }

    @Test
    void shouldResolveValidRequestPaths() {
        final ApacheHttpClientTransport transport = transport(SANDBOX);

        assertEquals("https://api.sandbox.checkout.com/payments/pay_123", transport.getRequestUrl(VALID_PATH));
        assertEquals("https://api.sandbox.checkout.com/payments/pay_1%252Frefunds", transport.getRequestUrl("payments/pay_1%252Frefunds"));
        assertEquals("https://api.sandbox.checkout.com/payments/...", transport.getRequestUrl("payments/..."));
        assertEquals("https://api.sandbox.checkout.com/ideal-external/issuers", transport.getRequestUrl("ideal-external/issuers"));
        assertEquals("https://api.sandbox.checkout.com/event-types?version=v2%26x%3D1%23y",
                transport.getRequestUrl("event-types?version=v2%26x%3D1%23y"));
    }

    @Test
    void shouldResolveUnderCustomBaseUris() {
        assertEquals("https://proxy.example/checkout/payments/pay_123",
                transport(URI.create("https://proxy.example/checkout/")).getRequestUrl(VALID_PATH));
        assertEquals("https://proxy.example/payments/pay_123",
                transport(URI.create("https://proxy.example")).getRequestUrl(VALID_PATH));
        assertEquals("https://proxy.example:8443/payments/pay_123",
                transport(URI.create("https://proxy.example:8443")).getRequestUrl(VALID_PATH));
        // Without a trailing slash, URI.resolve replaces the last base segment, as it always has.
        assertEquals("https://proxy.example/payments/pay_123",
                transport(URI.create("https://proxy.example/checkout")).getRequestUrl(VALID_PATH));
    }

    @Test
    void shouldResolveUnderBaseUrisWithUnderscoreHostsOrUnnormalisedPaths() {
        assertEquals("https://proxy_host.example/payments/pay_123",
                transport(URI.create("https://proxy_host.example/")).getRequestUrl(VALID_PATH));
        assertEquals("http://checkout_proxy:8080/api/payments/pay_123",
                transport(URI.create("http://checkout_proxy:8080/api/")).getRequestUrl(VALID_PATH));
        assertEquals("https://proxy.example/b/payments/pay_123",
                transport(URI.create("https://proxy.example/a/../b/")).getRequestUrl(VALID_PATH));
        assertEquals("https://proxy.example/payments/pay_123",
                transport(URI.create("https://proxy.example//")).getRequestUrl(VALID_PATH));
    }

    @ParameterizedTest
    @ValueSource(strings = {"api.example.com/", "/checkout/", "mailto:x@example.com", "https:///payments/"})
    void shouldRefuseBaseUrisThatAreNotAbsoluteWithAHost(final String baseUri) {
        assertThrows(CheckoutArgumentException.class, () -> transport(URI.create(baseUri)));
    }

    @Test
    void shouldNotSendTheAuthorizationHeaderWhenFollowingARedirect() {
        final List<String> targetAuthorization = Collections.synchronizedList(new ArrayList<>());
        server.createContext("/redirect", exchange -> {
            exchange.getResponseHeaders().add("Location", "/target");
            exchange.sendResponseHeaders(307, -1);
            exchange.close();
        });
        server.createContext("/target", exchange -> {
            targetAuthorization.add(String.valueOf(exchange.getRequestHeaders().getFirst("Authorization")));
            final byte[] body = "{}".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(body);
            }
        });

        localTransport().invokeSync(ClientOperation.GET, "redirect", authorization, null, null, Collections.emptyMap());

        assertEquals(Collections.singletonList("null"), targetAuthorization, "the redirect was followed without credentials");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            DOT_SEGMENT_PATH,
            "payments/./pay_1",
            "payments/..",
            "payments/%2e%2e/workflows/wf_1",
            "payments/%2E/pay_1",
            "payments/..;x/refunds",
            "payments//refunds",
            "payments/pay_1/",
            "/payments/pay_1",
            "//example.com/payments",
            "payments/pay_1/refunds?",
            "event-types?version=v2#x",
            "event-types?version=../x",
            "event-types?version=a?b",
            "event-types?version=%zz",
            "payments/%2F..%2Fworkflows",
            "payments/a%2fb",
            "payments/pay_1%2Frefunds",
            "payments/%5C..%5Cx",
            "payments/a%00b",
            "payments/a%0D%0Ab",
            "payments/a%7Fb",
            "payments/a%zzb",
            "payments/a%",
            "p\\..\\x",
            "payments/a b",
            "payments/a:b",
            "event-types?version=%2",
            "event-types?version=v2%",
            "a?b/../c",
            "payments/../x?version=v2",
            "payments/pay_1#refunds",
            "https:evil.example/payments",
            "https://evil.example/payments",
            "mailto:x"
    })
    void shouldRefuseInvalidRequestPaths(final String path) {
        assertThrows(CheckoutArgumentException.class, () -> transport(SANDBOX).getRequestUrl(path));
    }

    @ParameterizedTest
    @EnumSource(ClientOperation.class)
    void shouldSendValidPathsAndRefuseDotSegmentsOnEveryOperation(final ClientOperation operation) {
        final ApacheHttpClientTransport transport = localTransport();

        transport.invokeSync(operation, VALID_PATH, authorization, null, null, Collections.emptyMap());
        transport.invoke(operation, VALID_PATH, authorization, null, null, Collections.emptyMap()).join();
        assertEquals(Collections.nCopies(2, "/" + VALID_PATH), receivedPaths);

        assertThrows(CheckoutArgumentException.class,
                () -> transport.invokeSync(operation, DOT_SEGMENT_PATH, authorization, null, null, Collections.emptyMap()));
        assertRefusedAsync(() -> transport.invoke(operation, DOT_SEGMENT_PATH, authorization, null, null, Collections.emptyMap()).join());
        assertEquals(2, receivedPaths.size(), "no request may reach the server");
    }

    @Test
    void shouldRefuseLongInvalidSegmentsAndQueriesInLinearTime() {
        final String longSegment = repeat("%41a", 50_000) + "!";
        final String longQuery = repeat("a=%41&", 50_000) + "!";
        final ApacheHttpClientTransport transport = transport(SANDBOX);
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            assertThrows(CheckoutArgumentException.class, () -> transport.getRequestUrl("payments/" + longSegment));
            assertThrows(CheckoutArgumentException.class, () -> transport.getRequestUrl("payments?" + longQuery));
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"pay_123", "a", "%41", "%2e%2E", "A-Z_a.z~0-9", "%ff%00x"})
    void shouldAcceptEncodedSegments(final String segment) {
        assertTrue(ApacheHttpClientTransport.isPercentEncoded(segment, SEGMENT_CHARACTERS));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "%", "%4", "%4G", "a%", "a%4", "%%41", "a b", "a+b", "a*b", "a=b", "a&b", "a;b",
            "a/b", "a\\b", "a?b", "a#b", "café", "١", "Ａ", "a\u0000b"})
    void shouldRefuseSegmentsThatAreNotEncoded(final String segment) {
        assertFalse(ApacheHttpClientTransport.isPercentEncoded(segment, SEGMENT_CHARACTERS));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a=1", "a=1&b=2", "q=%20x", "a*b+c", "&&", "="})
    void shouldAcceptEncodedQueries(final String query) {
        assertTrue(ApacheHttpClientTransport.isPercentEncoded(query, QUERY_CHARACTERS));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a=1 b", "a=%2", "a=1;b", "a=1#f", "a=?", "a=/b", "a=café"})
    void shouldRefuseQueriesThatAreNotEncoded(final String query) {
        assertFalse(ApacheHttpClientTransport.isPercentEncoded(query, QUERY_CHARACTERS));
    }

    /**
     * Differential check against the patterns this validator replaces: same answer on random strings
     * built from every character class that matters (allowed, "%", hex and non-hex, reserved, non-ASCII).
     */
    @Test
    void shouldMatchThePreviousPatternsOnRandomInput() {
        final java.util.regex.Pattern segmentReference = java.util.regex.Pattern.compile("(?:[A-Za-z0-9._~-]|%[0-9A-Fa-f]{2})+");
        final java.util.regex.Pattern queryReference = java.util.regex.Pattern.compile("(?:[A-Za-z0-9._~*+=&-]|%[0-9A-Fa-f]{2})+");
        final String alphabet = "aZ09._~-*+=&%%%fFgG/\\?#; é١Ａ\u0000";
        final java.util.Random random = new java.util.Random(20261116L);
        for (int run = 0; run < 200_000; run++) {
            final StringBuilder value = new StringBuilder();
            final int length = random.nextInt(8);
            for (int i = 0; i < length; i++) {
                value.append(alphabet.charAt(random.nextInt(alphabet.length())));
            }
            final String candidate = value.toString();
            assertEquals(segmentReference.matcher(candidate).matches(),
                    ApacheHttpClientTransport.isPercentEncoded(candidate, SEGMENT_CHARACTERS), "segment: " + candidate);
            assertEquals(queryReference.matcher(candidate).matches(),
                    ApacheHttpClientTransport.isPercentEncoded(candidate, QUERY_CHARACTERS), "query: " + candidate);
        }
    }

    private static String repeat(final String value, final int times) {
        final StringBuilder builder = new StringBuilder(value.length() * times);
        for (int i = 0; i < times; i++) {
            builder.append(value);
        }
        return builder.toString();
    }

    @Test
    void shouldEncodeQueryParametersApartFromThePath() {
        localTransport().invokeSync(ClientOperation.QUERY, "event-types", authorization, null, null,
                Collections.singletonMap("version", "v2&x=1#y"));

        assertEquals(Collections.singletonList("/event-types"), receivedPaths);
        assertEquals(Collections.singletonList("version=v2%26x%3D1%23y"), receivedQueries);
    }

    @Test
    void shouldSendValidPathsAndRefuseDotSegmentsOnFileUploads() throws IOException {
        final ApacheHttpClientTransport transport = localTransport();
        final File file = new File(tempDir, "evidence.pdf");
        Files.write(file.toPath(), "%PDF-1.4".getBytes(StandardCharsets.US_ASCII));
        final FileRequest fileRequest = FileRequest.builder()
                .file(file)
                .contentType(ContentType.create("application/pdf"))
                .purpose(FilePurpose.DISPUTE_EVIDENCE)
                .build();

        transport.submitFileSync("files", authorization, fileRequest);
        transport.submitFile("files", authorization, fileRequest).join();
        assertEquals(Collections.nCopies(2, "/files"), receivedPaths);

        assertThrows(CheckoutArgumentException.class, () -> transport.submitFileSync("files/..", authorization, fileRequest));
        assertRefusedAsync(() -> transport.submitFile("files/..", authorization, fileRequest).join());
        assertEquals(2, receivedPaths.size(), "no request may reach the server");
    }

    private static void assertRefusedAsync(final Runnable call) {
        final CompletionException exception = assertThrows(CompletionException.class, call::run);
        assertInstanceOf(CheckoutArgumentException.class, exception.getCause());
        assertTrue(exception.getCause().getMessage().startsWith("Invalid request path"));
    }

}
