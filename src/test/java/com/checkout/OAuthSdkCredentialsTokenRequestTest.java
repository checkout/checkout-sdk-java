package com.checkout;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.sun.net.httpserver.HttpServer;

class OAuthSdkCredentialsTokenRequestTest {

    private static final String CLIENT_SECRET = "cs_hardening_value_42";
    private static final String ECHO = "{{request}}";
    private static final String TOKEN_REQUEST_FAILED = "OAuth token request failed with status ";
    private static final String INVALID_TOKEN_RESPONSE = "OAuth token response is invalid";
    private static final String INVALID_URI = "OAuth authorization URI is invalid";

    private HttpServer server;
    private volatile int status;
    private volatile String contentType;
    private volatile String body;
    private volatile String location;
    private final AtomicInteger requests = new AtomicInteger();
    private final AtomicInteger targetRequests = new AtomicInteger();

    @BeforeEach
    void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            requests.incrementAndGet();
            final String request = readAll(exchange.getRequestBody());
            final byte[] response = body.replace(ECHO, request).getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", contentType);
            if (location != null) {
                exchange.getResponseHeaders().add("Location", location);
            }
            exchange.sendResponseHeaders(status, response.length == 0 ? -1 : response.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(response);
            }
        });
        server.start();
    }

    @AfterEach
    void stopServer() {
        server.stop(0);
    }

    private static String readAll(final InputStream in) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        return out.toString(StandardCharsets.UTF_8.name());
    }

    private void respond(final int status, final String contentType, final String body) {
        this.status = status;
        this.contentType = contentType;
        this.body = body;
    }

    private URI tokenUri() {
        return URI.create("http://127.0.0.1:" + server.getAddress().getPort() + "/connect/token");
    }

    private static OAuthSdkCredentials credentials(final URI authorizationUri) {
        final RequestConfig timeouts = RequestConfig.custom()
                .setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        return new OAuthSdkCredentials(HttpClientBuilder.create().setDefaultRequestConfig(timeouts), authorizationUri,
                "client_id", CLIENT_SECRET, EnumSet.of(OAuthScope.GATEWAY));
    }

    private static int closedPort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) {
            return socket.getLocalPort();
        }
    }

    private CheckoutException requestFails() {
        final OAuthSdkCredentials credentials = credentials(tokenUri());
        return assertThrows(CheckoutException.class,
                () -> credentials.getAuthorization(SdkAuthorizationType.OAUTH));
    }

    private static void assertOnlyFixedText(final Throwable thrown) {
        for (Throwable t = thrown; t != null; t = t.getCause()) {
            final String text = String.valueOf(t.getMessage()) + " " + t;
            assertFalse(text.contains(CLIENT_SECRET), "unexpected text in " + t.getClass().getName() + ": " + text);
            assertFalse(text.contains("<html>"), "unexpected text in " + t.getClass().getName() + ": " + text);
            assertFalse(text.contains("tok_"), "unexpected text in " + t.getClass().getName() + ": " + text);
        }
    }

    /**
     * Answers one request on a raw socket with the given bytes, for responses HttpServer cannot produce.
     */
    private static URI rawServer(final ServerSocket socket, final String response) {
        final Thread thread = new Thread(() -> {
            try (Socket client = socket.accept()) {
                final InputStream in = client.getInputStream();
                final StringBuilder head = new StringBuilder();
                int read;
                while (head.indexOf("\r\n\r\n") < 0 && (read = in.read()) != -1) {
                    head.append((char) read);
                }
                final String lower = head.toString().toLowerCase();
                final int start = lower.indexOf("content-length:");
                final int length = Integer.parseInt(lower.substring(start + 15, lower.indexOf("\r\n", start)).trim());
                for (int i = 0; i < length; i++) {
                    in.read();
                }
                final OutputStream out = client.getOutputStream();
                out.write(response.getBytes(StandardCharsets.US_ASCII));
                out.flush();
            } catch (final IOException ignore) {
                // the test fails on the client side
            }
        });
        thread.setDaemon(true);
        thread.start();
        return URI.create("http://127.0.0.1:" + socket.getLocalPort() + "/connect/token");
    }

    private void assertTokenRequestFails(final String expectedMessage) {
        final CheckoutException exception = requestFails();
        assertInstanceOf(CheckoutAuthorizationException.class, exception);
        assertEquals(expectedMessage, exception.getMessage());
        assertOnlyFixedText(exception);
    }

    @Test
    void shouldRejectServerErrorWithHtmlBody() {
        respond(500, "text/html", "<html><body>upstream failed for " + ECHO + "</body></html>");

        assertTokenRequestFails(TOKEN_REQUEST_FAILED + 500);
    }

    @Test
    void shouldRejectServerErrorWithJsonBodyWithoutError() {
        respond(503, "application/json", "{\"access_token\":\"tok_1\",\"expires_in\":3600,\"echo\":\"" + ECHO + "\"}");

        assertTokenRequestFails(TOKEN_REQUEST_FAILED + 503);
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid_request", "invalid_client", "invalid_grant", "unauthorized_client",
            "unsupported_grant_type", "invalid_scope"})
    void shouldKeepTheOAuthErrorCodeMessage(final String code) {
        respond(400, "application/json", "{\"error\":\"" + code + "\",\"error_description\":\"" + ECHO + "\"}");

        assertTokenRequestFails("OAuth client_credentials authentication failed with error: " + code);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            CLIENT_SECRET,
            "tok_\\u0000x",
            "tok_\\u202ex",
            "tok_\\r\\nx",
            "INVALID_CLIENT",
            "invalid_client "
    })
    void shouldUseTheStatusMessageForAnUnknownErrorCode(final String error) {
        respond(400, "application/json", "{\"error\":\"" + error + "\"}");

        assertTokenRequestFails(TOKEN_REQUEST_FAILED + 400);
    }

    @Test
    void shouldRejectOkResponseWithAnUnknownError() {
        respond(200, "application/json",
                "{\"error\":\"" + CLIENT_SECRET + "\",\"access_token\":\"tok_1\",\"token_type\":\"Bearer\",\"expires_in\":3600}");

        assertTokenRequestFails(INVALID_TOKEN_RESPONSE);
    }

    @Test
    void shouldRaiseAnAuthorizationExceptionWhenTheTokenRequestFails() throws IOException {
        final OAuthSdkCredentials credentials = credentials(URI.create("http://127.0.0.1:" + closedPort() + "/connect/token"));

        final CheckoutException exception = assertThrows(CheckoutException.class,
                () -> credentials.getAuthorization(SdkAuthorizationType.OAUTH));

        assertInstanceOf(CheckoutAuthorizationException.class, exception);
        assertEquals("OAuth client_credentials authentication failed", exception.getMessage());
        assertInstanceOf(IOException.class, exception.getCause());
        assertOnlyFixedText(exception);
    }

    @Test
    void shouldRejectTokenResponseWithAnIllegalCharset() {
        respond(200, "application/json; charset=\"a b{}\"", "{\"access_token\":\"tok_1\",\"token_type\":\"Bearer\",\"expires_in\":3600}");

        final CheckoutException exception = requestFails();

        assertInstanceOf(CheckoutAuthorizationException.class, exception);
        assertEquals(INVALID_TOKEN_RESPONSE, exception.getMessage());
        assertNull(exception.getCause());
        assertFalse(exception.getMessage().contains("a b{}"));
    }

    @Test
    void shouldNotKeepTheBodyReadErrorAsCause() throws IOException {
        try (ServerSocket socket = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) {
            final URI uri = rawServer(socket, "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n"
                    + "Transfer-Encoding: chunked\r\n\r\ntok_in_chunk_header\r\n");
            final OAuthSdkCredentials credentials = credentials(uri);

            final CheckoutException exception = assertThrows(CheckoutException.class,
                    () -> credentials.getAuthorization(SdkAuthorizationType.OAUTH));

            assertInstanceOf(CheckoutAuthorizationException.class, exception);
            assertEquals(INVALID_TOKEN_RESPONSE, exception.getMessage());
            assertNull(exception.getCause());
            assertOnlyFixedText(exception);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "HTTP/1.1 200 OK\r\ntok_in_header_without_colon\r\n\r\n",
            "HTTP/1.1 2tok_0 OK\r\nContent-Length: 0\r\n\r\n"
    })
    void shouldNotKeepTheProtocolErrorAsCause(final String response) throws IOException {
        try (ServerSocket socket = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) {
            final OAuthSdkCredentials credentials = credentials(rawServer(socket, response));

            final CheckoutException exception = assertThrows(CheckoutException.class,
                    () -> credentials.getAuthorization(SdkAuthorizationType.OAUTH));

            assertInstanceOf(CheckoutAuthorizationException.class, exception);
            assertEquals(INVALID_TOKEN_RESPONSE, exception.getMessage());
            assertNull(exception.getCause());
            assertOnlyFixedText(exception);
        }
    }

    @Test
    void shouldRejectNonOkSuccessStatus() {
        respond(202, "application/json", "{\"access_token\":\"tok_1\",\"token_type\":\"Bearer\",\"expires_in\":3600}");

        assertTokenRequestFails(TOKEN_REQUEST_FAILED + 202);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "<html><body>" + ECHO + "</body></html>",
            "",
            "{}",
            "null",
            "[]",
            "{\"access_token\":null,\"token_type\":\"Bearer\",\"expires_in\":3600,\"echo\":\"" + ECHO + "\"}",
            "{\"access_token\":\"\",\"token_type\":\"Bearer\",\"expires_in\":3600}",
            "{\"access_token\":\"tok_1\",\"token_type\":\"Bearer\"}",
            "{\"access_token\":\"tok_1\",\"token_type\":\"Bearer\",\"expires_in\":null}",
            "{\"access_token\":\"tok_1\",\"token_type\":\"Bearer\",\"expires_in\":0}",
            "{\"access_token\":\"tok_1\",\"token_type\":\"Bearer\",\"expires_in\":-1}",
            "{\"access_token\":\"tok_1\",\"token_type\":\"Bearer\",\"expires_in\":9223372036854775807}",
            "{\"access_token\":\"tok_1\",\"token_type\":\"Bearer\",\"expires_in\":\"soon\"}"
    })
    void shouldRejectInvalidTokenResponse(final String responseBody) {
        respond(200, "application/json", responseBody);

        assertTokenRequestFails(INVALID_TOKEN_RESPONSE);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 3600, 31536001, 3153600000L})
    void shouldAcceptAPositiveTokenLifetime(final long expiresIn) {
        respond(200, "application/json",
                "{\"access_token\":\"tok_1\",\"token_type\":\"Bearer\",\"expires_in\":" + expiresIn + "}");

        final OAuthSdkCredentials credentials = credentials(tokenUri());
        final LocalDateTime before = LocalDateTime.now();
        final SdkAuthorization authorization = credentials.getAuthorization(SdkAuthorizationType.OAUTH);

        assertEquals("Bearer tok_1", authorization.getAuthorizationHeader());
        assertNotNull(credentials.getOAuthAccessToken());
        assertEquals("tok_1", credentials.getOAuthAccessToken().getToken());
        assertEquals("Bearer", credentials.getOAuthAccessToken().getTokenType());
        assertFalse(credentials.getOAuthAccessToken().getExpirationDate().isBefore(before.plusSeconds(expiresIn)));
    }

    /**
     * Token values of every shape: when the response is otherwise invalid, the error message is the fixed text and
     * carries none of the token.
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "tok_\\u0000x",
            "tok_\\u007fx",
            "tok_\\u0085x",
            "tok_\\u2028x",
            "tok_\\u202ex",
            "tok_\\uff54\\uff4f\\uff4b",
            "tok_\\u00e9",
            "tok_\\r\\n x",
            "tok_\\tx",
            "Bearer tok_\\r\\nx",
            "tok_"
    })
    void shouldKeepTheFixedMessageForAnyTokenValue(final String token) {
        respond(200, "application/json", "{\"access_token\":\"" + token + "\",\"token_type\":\"Bearer\"}");

        assertTokenRequestFails(INVALID_TOKEN_RESPONSE);
    }

    @Test
    void shouldKeepTheFixedMessageForAVeryLongToken() {
        final StringBuilder token = new StringBuilder("tok_");
        for (int i = 0; i < 100000; i++) {
            token.append('a');
        }
        respond(200, "application/json", "{\"access_token\":\"" + token + "\",\"expires_in\":0}");

        assertTokenRequestFails(INVALID_TOKEN_RESPONSE);
    }

    @Test
    void shouldKeepTheFixedMessageForAnUnexpectedResponseBody() {
        respond(200, "application/json", "{\"client_secret\":\"" + CLIENT_SECRET + "\",\"echo\":\"" + ECHO + "\"}");

        assertTokenRequestFails(INVALID_TOKEN_RESPONSE);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "http://access.checkout.com/connect/token",
            "http://127.0.0.1.example.com/connect/token",
            "http://localhost.example.com/connect/token",
            "http://127.0.0.1@example.com/connect/token",
            "http://0.0.0.0/connect/token",
            "http://128.0.0.1/connect/token",
            "http://[::2]/connect/token",
            "http://[::ffff:127.0.0.1]/connect/token",
            "http://127.1/connect/token",
            "http://127.0.0.01/connect/token",
            "http://127.0.0.256/connect/token",
            "http://0x7f.0.0.1/connect/token",
            "http://localhost.evil.com/connect/token",
            "http://127.0.0.1.nip.io/connect/token",
            "file:///etc/connect/token",
            "ftp://access.checkout.com/connect/token",
            "ws://localhost/connect/token",
            "connect/token",
            "test",
            "//access.checkout.com/connect/token",
            "https:/connect/token",
            "https:access.checkout.com",
            "https://user@/connect/token",
            "https://:443/connect/token",
            "mailto:ops@example.com",
            "https://user:pass@access.checkout.com/connect/token",
            "https://user@access.checkout.com/connect/token",
            "https://u%3Ap%40access.checkout.com/connect/token",
            "http://user@localhost/connect/token",
            "https://access.checkout.com:0/connect/token",
            "https://access.checkout.com:65536/connect/token",
            "http://localhost:99999/connect/token",
            "http://127.0.0.1:0/connect/token",
            "https://_x_:99999/connect/token",
            "https://acce\u0455s.checkout.com/connect/token",
            "https://b\u00fccher.example/connect/token",
            "http://l\u043ecalhost/connect/token"
    })
    void shouldRejectAuthorizationUriThatIsNotHttps(final String uri) {
        final CheckoutArgumentException exception = assertThrows(CheckoutArgumentException.class,
                () -> credentials(URI.create(uri)));

        assertEquals(INVALID_URI, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "https://access.checkout.com/connect/token",
            "HTTPS://access.checkout.com/connect/token",
            "https://proxy.example:8443/oauth/token?tenant=1",
            "http://localhost/connect/token",
            "http://LocalHost:8080/connect/token",
            "http://127.0.0.1/connect/token",
            "http://127.10.20.30:9000/connect/token",
            "http://[::1]:9000/connect/token",
            "https://access.checkout.com:1/connect/token",
            "https://access.checkout.com:65535/connect/token"
    })
    void shouldAcceptHttpsOrLoopbackAuthorizationUri(final String uri) {
        assertDoesNotThrow(() -> credentials(URI.create(uri)));
    }

    @Test
    @SuppressWarnings("deprecation")
    void shouldRejectHttpAuthorizationUriFromCustomEnvironment() {
        final AbstractCheckoutSdkBuilder<CheckoutApiImpl> builder = CheckoutSdk.builder()
                .oAuth()
                .clientCredentials("client_id", CLIENT_SECRET)
                .scopes(OAuthScope.GATEWAY)
                .environment(CustomEnvironment.builder()
                        .checkoutApi(URI.create("https://api.example.com/"))
                        .oAuthAuthorizationApi(URI.create("http://auth.example.com/connect/token"))
                        .build())
                .useLegacyDomain();

        final CheckoutArgumentException exception = assertThrows(CheckoutArgumentException.class, builder::build);

        assertEquals(INVALID_URI, exception.getMessage());
    }

    @Test
    @SuppressWarnings("deprecation")
    void shouldRejectHttpAuthorizationUriFromTheBuilder() {
        final AbstractCheckoutSdkBuilder<CheckoutApiImpl> builder = CheckoutSdk.builder()
                .oAuth()
                .clientCredentials(URI.create("http://auth.example.com/connect/token"), "client_id", CLIENT_SECRET)
                .scopes(OAuthScope.GATEWAY)
                .environment(Environment.SANDBOX)
                .useLegacyDomain();

        final CheckoutArgumentException exception = assertThrows(CheckoutArgumentException.class, builder::build);

        assertEquals(INVALID_URI, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {301, 302, 303, 307, 308})
    void shouldNotFollowARedirectOfTheTokenRequest(final int redirectStatus) throws IOException {
        final HttpServer target = tokenServer();
        try {
            location = "http://127.0.0.1:" + target.getAddress().getPort() + "/elsewhere";
            respond(redirectStatus, "text/plain", "");

            final CheckoutException exception = requestFails();

            assertInstanceOf(CheckoutAuthorizationException.class, exception);
            assertEquals(TOKEN_REQUEST_FAILED + redirectStatus, exception.getMessage());
            assertOnlyFixedText(exception);
            assertEquals(0, targetRequests.get());
        } finally {
            target.stop(0);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {301, 302, 303, 307, 308})
    void shouldNotFollowARedirectOfTheTokenRequestWithALaxRedirectStrategy(final int redirectStatus) throws IOException {
        final HttpServer target = tokenServer();
        try {
            location = "http://127.0.0.1:" + target.getAddress().getPort() + "/elsewhere";
            respond(redirectStatus, "text/plain", "");
            final OAuthSdkCredentials credentials = new OAuthSdkCredentials(
                    HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()), tokenUri(),
                    "client_id", CLIENT_SECRET, EnumSet.of(OAuthScope.GATEWAY));

            final CheckoutException exception = assertThrows(CheckoutException.class,
                    () -> credentials.getAuthorization(SdkAuthorizationType.OAUTH));

            assertEquals(TOKEN_REQUEST_FAILED + redirectStatus, exception.getMessage());
            assertEquals(0, targetRequests.get());
        } finally {
            target.stop(0);
        }
    }

    @Test
    void shouldKeepTheClientRequestConfigurationOfTheTokenRequest() throws IOException {
        try (ServerSocket socket = new ServerSocket(0, 1, InetAddress.getByName("127.0.0.1"))) {
            final URI uri = URI.create("http://127.0.0.1:" + socket.getLocalPort() + "/connect/token");
            final RequestConfig shortTimeout = RequestConfig.custom().setSocketTimeout(300).build();
            final OAuthSdkCredentials credentials = new OAuthSdkCredentials(
                    HttpClientBuilder.create().setDefaultRequestConfig(shortTimeout), uri,
                    "client_id", CLIENT_SECRET, EnumSet.of(OAuthScope.GATEWAY));

            final CheckoutException exception = assertTimeoutPreemptively(Duration.ofSeconds(10),
                    () -> assertThrows(CheckoutException.class,
                            () -> credentials.getAuthorization(SdkAuthorizationType.OAUTH)));

            assertInstanceOf(CheckoutAuthorizationException.class, exception);
            assertInstanceOf(SocketTimeoutException.class, exception.getCause());
        }
    }

    @Test
    void shouldNotRequestATokenWhenTheCredentialsAreCreated() {
        respond(200, "application/json", "{\"access_token\":\"tok_1\",\"expires_in\":3600}");

        final OAuthSdkCredentials credentials = credentials(tokenUri());

        assertEquals(0, requests.get());
        assertNotNull(credentials.getAuthorization(SdkAuthorizationType.OAUTH));
        assertEquals(1, requests.get());
    }

    @ParameterizedTest
    @ValueSource(strings = {"SECRET_KEY", "PUBLIC_KEY"})
    void shouldRejectAnOperationThatDoesNotAcceptOAuth(final String type) {
        respond(200, "application/json", "{\"access_token\":\"tok_1\",\"expires_in\":3600}");
        final SdkAuthorizationType authorizationType = SdkAuthorizationType.valueOf(type);

        final CheckoutAuthorizationException exception = assertThrows(CheckoutAuthorizationException.class,
                () -> credentials(tokenUri()).getAuthorization(authorizationType));

        assertEquals("Operation requires " + type + " authorization type", exception.getMessage());
        assertEquals(0, requests.get());
    }

    private HttpServer tokenServer() throws IOException {
        final HttpServer target = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        target.createContext("/", exchange -> {
            targetRequests.incrementAndGet();
            final byte[] response = "{\"access_token\":\"tok_from_redirect\",\"expires_in\":3600}"
                    .getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(response);
            }
        });
        target.start();
        return target;
    }

}
