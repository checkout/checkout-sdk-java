package com.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.HttpServer;

class ApacheHttpClientTransportDuplicateHeadersTest {

    private static final String BODY = "{\"id\":\"pay_123\"}";

    private final SdkAuthorization authorization = new SdkAuthorization(PlatformType.DEFAULT, "sk_test");
    private HttpServer server;
    private ExecutorService executor;

    @BeforeEach
    void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            final byte[] body = BODY.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.getResponseHeaders().add("Set-Cookie", "a=1");
            exchange.getResponseHeaders().add("Set-Cookie", "b=2");
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(body);
            }
        });
        server.start();
        executor = Executors.newSingleThreadExecutor();
    }

    @AfterEach
    void stop() {
        server.stop(0);
        executor.shutdownNow();
    }

    private ApacheHttpClientTransport transport() {
        return new ApacheHttpClientTransport(URI.create("http://127.0.0.1:" + server.getAddress().getPort() + "/"),
                "checkoutApi", HttpClientBuilder.create(), executor,
                new DefaultTransportConfiguration(), mock(CheckoutConfiguration.class));
    }

    /** The JDK test server normalises header-name casing, so look the header up case-insensitively. */
    private static String setCookie(final Response response) {
        return response.getHeaders().entrySet().stream()
                .filter(e -> "set-cookie".equalsIgnoreCase(e.getKey()))
                .map(java.util.Map.Entry::getValue)
                .findFirst().orElse(null);
    }

    @Test
    void shouldReturnResponseWhenServerSendsRepeatedHeadersSync() {
        final Response response = transport().invokeSync(ClientOperation.GET, "payments/pay_123", authorization, null, null, Collections.emptyMap());
        assertEquals(200, response.getStatusCode());
        assertEquals(BODY, response.getBody());
        assertEquals("a=1", setCookie(response));
    }

    @Test
    void shouldReturnResponseWhenServerSendsRepeatedHeadersAsync() throws Exception {
        final Response response = transport().invoke(ClientOperation.GET, "payments/pay_123", authorization, null, null, Collections.emptyMap()).get();
        assertEquals(200, response.getStatusCode());
        assertEquals(BODY, response.getBody());
        assertEquals("a=1", setCookie(response));
    }
}
