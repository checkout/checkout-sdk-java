package com.checkout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.HttpServer;

class ApacheHttpClientTransportLoggingTest {

    private static final String QUERY_VALUE = "jane.doe%40example.com";
    private static final String COOKIE = "session=ck_hardening_77";
    private static final String UNLISTED_HEADER = "X-Unlisted-Header";

    private final SdkAuthorization authorization = new SdkAuthorization(PlatformType.DEFAULT, "sk_test");
    private final List<String> messages = Collections.synchronizedList(new ArrayList<>());
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private HttpServer server;
    private Logger transportLogger;
    private AbstractAppender appender;

    @BeforeEach
    void setUp() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            final byte[] body = "{}".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.getResponseHeaders().add("Cko-Request-Id", "req_hardening_1");
            exchange.getResponseHeaders().add("Cko-Version", "1.0.0");
            exchange.getResponseHeaders().add("Set-Cookie", COOKIE);
            exchange.getResponseHeaders().add(UNLISTED_HEADER, "unlisted-value");
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(body);
            }
        });
        server.start();

        appender = new AbstractAppender("capture", null, null, true, Property.EMPTY_ARRAY) {
            @Override
            public void append(final LogEvent event) {
                messages.add(event.getMessage().getFormattedMessage());
            }
        };
        appender.start();
        transportLogger = (Logger) LogManager.getLogger(ApacheHttpClientTransport.class);
        transportLogger.addAppender(appender);
    }

    @AfterEach
    void tearDown() {
        transportLogger.removeAppender(appender);
        appender.stop();
        server.stop(0);
        executor.shutdownNow();
    }

    private ApacheHttpClientTransport transport() {
        return new ApacheHttpClientTransport(URI.create("http://127.0.0.1:" + server.getAddress().getPort() + "/"),
                "checkoutApi", HttpClientBuilder.create(), executor, new DefaultTransportConfiguration(), mock(CheckoutConfiguration.class));
    }

    private String allMessages() {
        return String.join("\n", messages);
    }

    @Test
    void shouldLogTheRequestPathWithoutTheQueryParameters() {
        final Response response = transport().invokeSync(ClientOperation.QUERY, "payments", authorization, null, null,
                Collections.singletonMap("reference", "jane.doe@example.com"));

        assertEquals(200, response.getStatusCode());
        assertTrue(messages.contains("QUERY: /payments"), allMessages());
        assertFalse(allMessages().contains("jane.doe"), allMessages());
        assertFalse(allMessages().contains("reference="), allMessages());
    }

    @Test
    void shouldLogTheRequestPathWithoutAQueryBuiltIntoThePath() {
        transport().invokeSync(ClientOperation.GET, "payments?reference=" + QUERY_VALUE, authorization, null, null, null);

        assertTrue(messages.contains("GET: /payments"), allMessages());
        assertFalse(allMessages().contains(QUERY_VALUE), allMessages());
    }

    @Test
    void shouldMaskAnEmailAddressInTheLoggedPath() {
        transport().invokeSync(ClientOperation.GET, "customers/jane.doe%40example.com", authorization, null, null, null);

        assertTrue(messages.contains("GET: /customers/***"), allMessages());
        assertFalse(allMessages().contains("jane.doe"), allMessages());
    }

    @Test
    void shouldFormatTheLoggedPath() {
        assertEquals("/customers/***", ApacheHttpClientTransport.loggablePath("/customers/jane.doe%40example.com"));
        assertEquals("/customers/***", ApacheHttpClientTransport.loggablePath("/customers/jane.doe%40EXAMPLE.com"));
        assertEquals("/customers/***/x", ApacheHttpClientTransport.loggablePath("/customers/a@b/x"));
        assertEquals("/customers/***", ApacheHttpClientTransport.loggablePath("/customers/jane%2540example.com"));
        assertEquals("/customers/***", ApacheHttpClientTransport.loggablePath("/customers/jane%252540example.com"));
        assertEquals("/customers/***", ApacheHttpClientTransport.loggablePath("/customers/jane%EF%BC%A0example.com"));
        assertEquals("/customers/***", ApacheHttpClientTransport.loggablePath("/customers/jane\uff20example.com"));
        assertEquals("/customers/***", ApacheHttpClientTransport.loggablePath("/customers/jane\ufe6bexample.com"));
        assertEquals("/customers/***", ApacheHttpClientTransport.loggablePath("/customers/jane%EF%B9%ABexample.com"));
        assertEquals("/customers/***", ApacheHttpClientTransport.loggablePath("/customers/jane%25252525252540x"));
        assertEquals("/customers/cus_1%25", ApacheHttpClientTransport.loggablePath("/customers/cus_1%25"));
        assertEquals("/customers/cus_%zz", ApacheHttpClientTransport.loggablePath("/customers/cus_%zz"));
        assertEquals("/payments/pay_1", ApacheHttpClientTransport.loggablePath("/payments/pay_1"));
        assertEquals("/a%E2%80%AEb", ApacheHttpClientTransport.loggablePath("/a\u202eb"));
        assertEquals("/a%E2%80%A8b%0D%0A", ApacheHttpClientTransport.loggablePath("/a\u2028b\r\n"));
        assertEquals("/a%C2%85%00", ApacheHttpClientTransport.loggablePath("/a\u0085\u0000"));
        assertEquals("/%0D%0A", ApacheHttpClientTransport.loggablePath("/%0D%0A"));
        assertEquals("/caf\u00e9", ApacheHttpClientTransport.loggablePath("/caf\u00e9"));
        assertEquals("/a%E2%80%A9b", ApacheHttpClientTransport.loggablePath("/a\u2029b"));
        assertEquals("/a\u00a0b", ApacheHttpClientTransport.loggablePath("/a\u00a0b"));
        assertEquals("/files/***/x", ApacheHttpClientTransport.loggablePath("/files/release@v2/x"));
        assertEquals("/files/f%2525F0%25259F%252598%252580",
                ApacheHttpClientTransport.loggablePath("/files/f%2525F0%25259F%252598%252580"));
        assertEquals("/files/\uD83D\uDE00", ApacheHttpClientTransport.loggablePath("/files/\uD83D\uDE00"));
        assertEquals("/customers/***", ApacheHttpClientTransport.loggablePath("/customers/\uD83D\uDE00%252540x"));
        assertEquals("", ApacheHttpClientTransport.loggablePath(""));
        assertEquals(null, ApacheHttpClientTransport.loggablePath(null));
    }

    @Test
    void shouldEscapeControlAndFormatCharactersInTheLoggedResponseHeaders() throws Exception {
        final byte[] value = "req_\u001b[2J\u0007\u0000\u202ex".getBytes(StandardCharsets.UTF_8);
        try (ServerSocket socket = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) {
            final Thread thread = new Thread(() -> {
                try (Socket client = socket.accept()) {
                    final InputStream in = client.getInputStream();
                    final StringBuilder head = new StringBuilder();
                    int read;
                    while (head.indexOf("\r\n\r\n") < 0 && (read = in.read()) != -1) {
                        head.append((char) read);
                    }
                    final OutputStream out = client.getOutputStream();
                    out.write("HTTP/1.1 204 No Content\r\nCko-Request-Id: ".getBytes(StandardCharsets.US_ASCII));
                    out.write(value);
                    out.write("\r\nConnection: close\r\n\r\n".getBytes(StandardCharsets.US_ASCII));
                    out.flush();
                } catch (final IOException ignore) {
                    // the test fails on the client side
                }
            });
            thread.setDaemon(true);
            thread.start();
            new ApacheHttpClientTransport(URI.create("http://127.0.0.1:" + socket.getLocalPort() + "/"),
                    "checkoutApi", HttpClientBuilder.create(), executor, new DefaultTransportConfiguration(), mock(CheckoutConfiguration.class))
                    .invokeSync(ClientOperation.GET, "payments", authorization, null, null, null);
        }

        final String responseLine = messages.stream().filter(it -> it.startsWith("Response: ")).findFirst().orElse("");
        assertTrue(responseLine.startsWith("Response: 204 ["), allMessages());
        assertTrue(responseLine.contains("req_%1B[2J%07%00"), responseLine);
        responseLine.codePoints().forEach(c -> {
            final int type = Character.getType(c);
            assertFalse(Character.isISOControl(c) || type == Character.FORMAT, responseLine);
        });
    }

    @Test
    void shouldLogOnlyAllowlistedResponseHeaders() {
        transport().invokeSync(ClientOperation.GET, "payments/pay_1", authorization, null, null, null);

        // com.sun.net.httpserver sends header names as "Cko-request-id", so names are compared in lower case.
        final String responseLine = messages.stream().filter(it -> it.startsWith("Response: ")).findFirst().orElse("");
        final String lowerCase = responseLine.toLowerCase(Locale.ROOT);
        final String everything = allMessages().toLowerCase(Locale.ROOT);
        assertTrue(responseLine.startsWith("Response: 200 ["), allMessages());
        assertTrue(lowerCase.contains("cko-request-id: req_hardening_1"), responseLine);
        assertTrue(lowerCase.contains("cko-version: 1.0.0"), responseLine);
        assertTrue(lowerCase.contains("content-type: application/json"), responseLine);
        assertTrue(lowerCase.contains("content-length: 2"), responseLine);
        assertTrue(lowerCase.contains("date: "), responseLine);
        assertFalse(everything.contains(COOKIE), allMessages());
        assertFalse(everything.contains("set-cookie"), allMessages());
        assertFalse(everything.contains(UNLISTED_HEADER.toLowerCase(Locale.ROOT)), allMessages());
        assertFalse(everything.contains("unlisted-value"), allMessages());
    }

}
