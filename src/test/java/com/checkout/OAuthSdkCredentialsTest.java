package com.checkout;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Objects;

import static com.checkout.PlatformType.DEFAULT_OAUTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OAuthSdkCredentialsTest {

    private static final HttpClientBuilder DEFAULT_CLIENT_BUILDER = HttpClientBuilder.create();

    private static final String BEARER = "Bearer ";

    @Mock
    private CloseableHttpClient client;

    @Mock
    private GsonSerializer serializer;

    @Test
    void shouldFailCreatingDefaultOAuthSdkCredentials() {
        try {
            new OAuthSdkCredentials(DEFAULT_CLIENT_BUILDER, null, "client_id", "client_secret", EnumSet.allOf(OAuthScope.class));
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("authorizationUri cannot be null", e.getMessage());
        }
        try {
            new OAuthSdkCredentials(DEFAULT_CLIENT_BUILDER, Mockito.mock(URI.class), null, "client_secret", EnumSet.allOf(OAuthScope.class));
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("clientId cannot be null", e.getMessage());
        }
        try {
            new OAuthSdkCredentials(DEFAULT_CLIENT_BUILDER, Mockito.mock(URI.class), "client_id", " ", EnumSet.allOf(OAuthScope.class));
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("clientSecret cannot be blank", e.getMessage());
        }
        try {
            new OAuthSdkCredentials(DEFAULT_CLIENT_BUILDER, Mockito.mock(URI.class), "client_id", "client_secret", null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("scopes cannot be null", e.getMessage());
        }
    }

    @Test
    void shouldCreateOAuthSdkCredentials() {

        final SdkCredentials credentials = new OAuthSdkCredentials(DEFAULT_CLIENT_BUILDER, Mockito.mock(URI.class), "client_id", "client_secret", EnumSet.allOf(OAuthScope.class));
        assertEquals(DEFAULT_OAUTH, credentials.getPlatformType());

    }

    @Test
    void authorization_shouldNotGetWhenTheresAValidAccessToken() {

        final OAuthSdkCredentials credentials = new OAuthSdkCredentials(DEFAULT_CLIENT_BUILDER, Mockito.mock(URI.class), "client_id", "client_secret", EnumSet.allOf(OAuthScope.class));
        credentials.setAccessToken(new OAuthAccessToken("y123", "Bearer", LocalDateTime.now().plusMinutes(5)));
        credentials.setClient(client);
        credentials.setSerializer(serializer);

        final SdkAuthorization auth1 = credentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH);

        assertNotNull(auth1);
        assertEquals(BEARER + "y123", auth1.getAuthorizationHeader());

        verifyNoInteractions(client);
        verifyNoInteractions(serializer);

    }

    @Test
    void authorization_shouldGetWhenTheresNoValidAccessToken() throws IOException, URISyntaxException {

        final InputStream stream = new ByteArrayInputStream((
                "{ \"access_token\" : \"y12345678\", " +
                        "\"expires_in\" : \"3600\", " +
                        "\"token_type\" : \"Bearer\", " +
                        "\"scope\" : \"fx gateway vault\" }").getBytes());

        final HttpEntity entity = Mockito.mock(HttpEntity.class);
        when(entity.getContent()).thenReturn(stream);

        final CloseableHttpResponse response = Mockito.mock(CloseableHttpResponse.class);
        when(response.getEntity()).thenReturn(entity);
        when(client.execute(Mockito.argThat(req -> req instanceof HttpPost &&
                Objects.equals(req.getURI().toString(), "https://test.checkout.com/oauth/token"))))
                .thenReturn(response);

        final OAuthSdkCredentials credentials = new OAuthSdkCredentials(
                DEFAULT_CLIENT_BUILDER,
                new URI("https://test.checkout.com/oauth/token"),
                "client_id",
                "client_secret",
                EnumSet.allOf(OAuthScope.class));

        credentials.setAccessToken(null);
        credentials.setClient(client);

        final SdkAuthorization auth1 = credentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY_OR_OAUTH);

        assertNotNull(auth1);
        assertEquals(BEARER + "y12345678", auth1.getAuthorizationHeader());

        final OAuthAccessToken accessToken = credentials.getOAuthAccessToken();
        assertNotNull(accessToken);
        assertEquals("y12345678", accessToken.getToken());
        assertTrue(accessToken.getExpirationDate().isAfter(LocalDateTime.now().plusSeconds(3598)));

    }

    @Test
    void authorization_shouldGetWhenTheresAnExpiredAccessToken() throws IOException, URISyntaxException {

        final InputStream stream = new ByteArrayInputStream((
                "{ \"access_token\" : \"y12345678\", " +
                        "\"expires_in\" : \"3600\", " +
                        "\"token_type\" : \"Bearer\", " +
                        "\"scope\" : \"fx gateway vault\" }").getBytes());

        final HttpEntity entity = Mockito.mock(HttpEntity.class);
        when(entity.getContent()).thenReturn(stream);

        final CloseableHttpResponse response = Mockito.mock(CloseableHttpResponse.class);
        when(response.getEntity()).thenReturn(entity);
        when(client.execute(any(HttpPost.class))).thenReturn(response);

        final OAuthSdkCredentials credentials = new OAuthSdkCredentials(
                DEFAULT_CLIENT_BUILDER,
                new URI("https://test.checkout.com/oauth/token"),
                "client_id",
                "client_secret",
                EnumSet.allOf(OAuthScope.class));

        credentials.setAccessToken(new OAuthAccessToken("y987654321", "Bearer", LocalDateTime.now().minusSeconds(30)));
        credentials.setClient(client);

        final SdkAuthorization auth1 = credentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY_OR_OAUTH);

        assertNotNull(auth1);
        assertEquals(BEARER + "y12345678", auth1.getAuthorizationHeader());

        final OAuthAccessToken accessToken = credentials.getOAuthAccessToken();
        assertNotNull(accessToken);
        assertEquals("y12345678", accessToken.getToken());
        assertTrue(accessToken.getExpirationDate().isAfter(LocalDateTime.now().plusSeconds(3598)));

    }

    @Test
    void shouldThrowExceptionWhenOAuthResponseIsNull() throws IOException {
        final InputStream stream = new ByteArrayInputStream("{}".getBytes());

        final HttpEntity entity = Mockito.mock(HttpEntity.class);
        when(entity.getContent()).thenReturn(stream);

        final CloseableHttpResponse response = Mockito.mock(CloseableHttpResponse.class);
        when(response.getEntity()).thenReturn(entity);
        when(client.execute(any(HttpPost.class))).thenReturn(response);

        final OAuthSdkCredentials credentials = new OAuthSdkCredentials(
                DEFAULT_CLIENT_BUILDER,
                URI.create("http://test.checkout.com/oauth/token"),
                "client_id",
                "client_secret",
                EnumSet.allOf(OAuthScope.class));

        credentials.setClient(client);

        CheckoutException exception = assertThrows(
                CheckoutException.class,
                () -> credentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY_OR_OAUTH)
        );

        assertEquals("Invalid OAuth response: {}", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAccessTokenIsNull() throws IOException {
        final InputStream stream = new ByteArrayInputStream((
                "{ \"access_token\": null, " +
                        "\"token_type\": \"Bearer\", " +
                        "\"expires_in\": 3600 }").getBytes());

        final HttpEntity entity = Mockito.mock(HttpEntity.class);
        when(entity.getContent()).thenReturn(stream);

        final CloseableHttpResponse response = Mockito.mock(CloseableHttpResponse.class);
        when(response.getEntity()).thenReturn(entity);
        when(client.execute(any(HttpPost.class))).thenReturn(response);

        final OAuthSdkCredentials credentials = new OAuthSdkCredentials(
                DEFAULT_CLIENT_BUILDER,
                URI.create("https://test.checkout.com/oauth/token"),
                "client_id",
                "client_secret",
                EnumSet.allOf(OAuthScope.class));

        credentials.setClient(client);

        CheckoutException exception = assertThrows(
                CheckoutException.class,
                () -> credentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY_OR_OAUTH)
        );

        assertEquals("Invalid OAuth response: { \"access_token\": null, \"token_type\": \"Bearer\", \"expires_in\": 3600 }",
                exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenOAuthResponseContainsError() throws IOException {
        final InputStream stream = new ByteArrayInputStream(("{ \"error\" : \"invalid_client\" }").getBytes());

        final HttpEntity entity = Mockito.mock(HttpEntity.class);
        when(entity.getContent()).thenReturn(stream);

        final CloseableHttpResponse response = Mockito.mock(CloseableHttpResponse.class);
        when(response.getEntity()).thenReturn(entity);
        when(client.execute(any(HttpPost.class))).thenReturn(response);

        final OAuthSdkCredentials credentials = new OAuthSdkCredentials(
                DEFAULT_CLIENT_BUILDER,
                URI.create("https://test.checkout.com/oauth/token"),
                "client_id",
                "client_secret",
                EnumSet.allOf(OAuthScope.class));

        credentials.setClient(client);

        CheckoutException exception = assertThrows(
                CheckoutException.class,
                () -> credentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY_OR_OAUTH)
        );

        assertEquals("OAuth client_credentials authentication failed with error: invalid_client", exception.getMessage());
    }

}