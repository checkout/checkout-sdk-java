package com.checkout;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
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

import static com.checkout.PlatformType.FOUR_OAUTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FourOAuthSdkCredentialsTest {

    private static final String BEARER = "Bearer ";

    @Mock
    private CloseableHttpClient client;

    @Mock
    private GsonSerializer serializer;

    @Test
    void shouldFailCreatingFourOAuthFourSdkCredentials() {
        try {
            new FourOAuthSdkCredentials(null, "client_id", "client_secret", EnumSet.allOf(FourOAuthScope.class));
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("authorizationUri cannot be null", e.getMessage());
        }
        try {
            new FourOAuthSdkCredentials(Mockito.mock(URI.class), null, "client_secret", EnumSet.allOf(FourOAuthScope.class));
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("clientId cannot be null", e.getMessage());
        }
        try {
            new FourOAuthSdkCredentials(Mockito.mock(URI.class), "client_id", " ", EnumSet.allOf(FourOAuthScope.class));
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("clientSecret cannot be blank", e.getMessage());
        }
        try {
            new FourOAuthSdkCredentials(Mockito.mock(URI.class), "client_id", "client_secret", null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("scopes cannot be null", e.getMessage());
        }
    }

    @Test
    void shouldCreateFourOAuthFourSdkCredentials() {

        final SdkCredentials credentials = new FourOAuthSdkCredentials(Mockito.mock(URI.class), "client_id", "client_secret", EnumSet.allOf(FourOAuthScope.class));
        assertEquals(FOUR_OAUTH, credentials.getPlatformType());

    }

    @Test
    void authorization_shouldNotGetWhenTheresAValidAccessToken() {

        final FourOAuthSdkCredentials credentials = new FourOAuthSdkCredentials(Mockito.mock(URI.class), "client_id", "client_secret", EnumSet.allOf(FourOAuthScope.class));
        credentials.setAccessToken(new OAuthAccessToken("y123", LocalDateTime.now().plusMinutes(5)));
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
        when(client.execute(any(HttpPost.class))).thenReturn(response);

        final FourOAuthSdkCredentials credentials = new FourOAuthSdkCredentials(
                new URI("http://test.checkout.com/oauth/token"),
                "client_id",
                "client_secret",
                EnumSet.allOf(FourOAuthScope.class));

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

        final FourOAuthSdkCredentials credentials = new FourOAuthSdkCredentials(
                new URI("http://test.checkout.com/oauth/token"),
                "client_id",
                "client_secret",
                EnumSet.allOf(FourOAuthScope.class));

        credentials.setAccessToken(new OAuthAccessToken("y987654321", LocalDateTime.now().minusSeconds(30)));
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
    void authorization_shouldFailAuthorizing() throws IOException, URISyntaxException {

        final InputStream stream = new ByteArrayInputStream(("{ \"error\" : \"super_error\" }").getBytes());

        final HttpEntity entity = Mockito.mock(HttpEntity.class);
        when(entity.getContent()).thenReturn(stream);

        final CloseableHttpResponse response = Mockito.mock(CloseableHttpResponse.class);
        when(response.getEntity()).thenReturn(entity);
        when(client.execute(any(HttpPost.class))).thenReturn(response);

        final FourOAuthSdkCredentials credentials = new FourOAuthSdkCredentials(
                new URI("http://test.checkout.com/oauth/token"),
                "fake",
                "fake",
                EnumSet.allOf(FourOAuthScope.class));

        credentials.setAccessToken(null);
        credentials.setClient(client);

        try {
            credentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY_OR_OAUTH);
            fail();
        } catch (final CheckoutException e) {
            assertEquals("OAuth client_credentials authentication failed with error: super_error", e.getMessage());
        }

        final OAuthAccessToken accessToken = credentials.getOAuthAccessToken();
        assertNull(accessToken);

    }

}