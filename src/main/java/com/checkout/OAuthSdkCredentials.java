package com.checkout;

import com.google.gson.annotations.SerializedName;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.ContentType;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.checkout.common.CheckoutUtils.validateParams;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;

@Slf4j
final class OAuthSdkCredentials extends SdkCredentials {

    private final URI authorizationUri;
    private final String clientId;
    private final String clientSecret;
    private final Set<OAuthScope> scopes;

    private CloseableHttpClient client;
    private GsonSerializer serializer;

    private OAuthAccessToken accessToken;

    OAuthSdkCredentials(final HttpClientBuilder httpClientBuilder,
                        final URI authorizationUri,
                        final String clientId,
                        final String clientSecret,
                        final Set<OAuthScope> scopes) {
        super(PlatformType.DEFAULT_OAUTH);
        validateParams("httpClientBuilder", httpClientBuilder, "authorizationUri", authorizationUri,
                "clientId", clientId, "clientSecret", clientSecret, "scopes", scopes);
        this.client = httpClientBuilder.build();
        this.authorizationUri = authorizationUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scopes = scopes;
        this.serializer = new GsonSerializer();
    }

    void initOAuthAccess() {
        getAccessToken();
    }

    @Override
    public SdkAuthorization getAuthorization(final SdkAuthorizationType authorizationType) {
        switch (authorizationType) {
            case SECRET_KEY_OR_OAUTH:
            case PUBLIC_KEY_OR_OAUTH:
            case OAUTH:
                return new SdkAuthorization(platformType, getAccessToken().getToken());
            default:
                throw CheckoutAuthorizationException.invalidAuthorization(authorizationType);
        }
    }

    private synchronized OAuthAccessToken getAccessToken() {
        if (accessToken != null && accessToken.isValid()) {
            log.debug("Using cached OAuth token");
            return accessToken;
        }
        final HttpPost httpPost = new HttpPost(authorizationUri);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
        final List<NameValuePair> data = Arrays.asList(
                new BasicNameValuePair("grant_type", "client_credentials"),
                new BasicNameValuePair("client_id", clientId),
                new BasicNameValuePair("client_secret", clientSecret),
                new BasicNameValuePair("scope", scopes.stream().map(OAuthScope::getScope).collect(Collectors.joining(" "))));
        httpPost.setEntity(new UrlEncodedFormEntity(data));
        log.debug("requesting OAuth token using client_credentials flow");
        try (final CloseableHttpResponse response = client.execute(httpPost)) {
            final String json = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
            final OAuthServiceResponse oAuthServiceResponse = serializer.fromJson(json, OAuthServiceResponse.class);
            if (oAuthServiceResponse == null) {
                throw new CheckoutException(format("Invalid OAuth response: %s", json));
            }

            if (oAuthServiceResponse.error != null) {
                throw new CheckoutException(format("OAuth client_credentials authentication failed with error: %s", oAuthServiceResponse.error));
            }

            if (oAuthServiceResponse.accessToken == null) {
                throw new CheckoutException(format("Invalid OAuth response: %s", json));
            }

            accessToken = new OAuthAccessToken(
                    oAuthServiceResponse.accessToken,
                    oAuthServiceResponse.tokenType,
                    now().plusSeconds(oAuthServiceResponse.expiresIn)
            );

            log.debug("OAuth token successfully retrieved, expires at: {}", accessToken.getExpirationDate());
            return accessToken;
        } catch (final ParseException e) {
            throw new CheckoutException("Failed to parse response body", e);
        } catch (final IOException e) {
            throw new CheckoutException("OAuth client_credentials authentication failed", e);
        }
    }

    synchronized void setAccessToken(final OAuthAccessToken accessToken) {
        this.accessToken = accessToken;
    }

    void setClient(final CloseableHttpClient client) {
        this.client = client;
    }

    void setSerializer(final GsonSerializer serializer) {
        this.serializer = serializer;
    }

    OAuthAccessToken getOAuthAccessToken() {
        return accessToken;
    }

    private static class OAuthServiceResponse {

        @SerializedName("access_token")
        String accessToken;

        @SerializedName("token_type")
        String tokenType;

        @SerializedName("expires_in")
        Long expiresIn;

        String error;

    }

}

