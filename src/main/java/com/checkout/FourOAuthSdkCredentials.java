package com.checkout;

import com.google.gson.annotations.SerializedName;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

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
final class FourOAuthSdkCredentials extends SdkCredentials {

    private final URI authorizationUri;
    private final String clientId;
    private final String clientSecret;
    private final Set<FourOAuthScope> scopes;

    private CloseableHttpClient client;
    private GsonSerializer serializer;

    private OAuthAccessToken accessToken;

    FourOAuthSdkCredentials(final URI authorizationUri, final String clientId, final String clientSecret, final Set<FourOAuthScope> scopes) {
        super(PlatformType.FOUR_OAUTH);
        validateParams("authorizationUri", authorizationUri, "clientId", clientId, "clientSecret", clientSecret, "scopes", scopes);
        this.authorizationUri = authorizationUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scopes = scopes;
        this.client = HttpClientBuilder.create().build();
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
            return accessToken;
        }
        final HttpPost httpPost = new HttpPost(authorizationUri);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
        try {
            final List<NameValuePair> data = Arrays.asList(
                    new BasicNameValuePair("client_id", clientId),
                    new BasicNameValuePair("client_secret", clientSecret),
                    new BasicNameValuePair("grant_type", "client_credentials"),
                    new BasicNameValuePair("scope", scopes.stream().map(FourOAuthScope::getScope).collect(Collectors.joining(" "))));
            httpPost.setEntity(new UrlEncodedFormEntity(data));
        } catch (final UnsupportedEncodingException ignore) {
            throw new CheckoutException("failed to encode oAuth URI");
        }
        log.debug("requesting OAuth token using client_credentials flow");
        try (final CloseableHttpResponse response = client.execute(httpPost)) {
            final String json = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
            final OAuthServiceResponse oAuthServiceResponse = serializer.fromJson(json, OAuthServiceResponse.class);
            if (oAuthServiceResponse.error != null) {
                throw new CheckoutException(format("OAuth client_credentials authentication failed with error: %s", oAuthServiceResponse.error));
            }
            accessToken = new OAuthAccessToken(oAuthServiceResponse.accessToken, now().plusSeconds(oAuthServiceResponse.expiresIn));
            return accessToken;
        } catch (final IOException e) {
            throw new CheckoutException("OAuth client_credentials authentication failed", e);
        }

    }

    private static class OAuthServiceResponse {

        @SerializedName("access_token")
        String accessToken;

        @SerializedName("expires_in")
        Long expiresIn;

        String error;

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

    synchronized void setAccessToken(final OAuthAccessToken accessToken) {
        this.accessToken = accessToken;
    }

}

