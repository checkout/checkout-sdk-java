package com.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
        assertEquals("https://api.sandbox.checkout.com/payments/pay_1%2Frefunds", transport.getRequestUrl("payments/pay_1%2Frefunds"));
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
