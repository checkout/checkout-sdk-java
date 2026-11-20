package com.checkout;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.checkout.common.CheckoutUtils.validateParams;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;

/**
 * OAuth client credentials: requests an access token from the authorization URI with the client id, secret and
 * scopes, caches it and renews it when it expires.
 */
@Slf4j
final class OAuthSdkCredentials extends SdkCredentials {

    private static final String INVALID_AUTHORIZATION_URI = "OAuth authorization URI is invalid";
    private static final String TOKEN_REQUEST_FAILED = "OAuth token request failed with status ";
    private static final String INVALID_TOKEN_RESPONSE = "OAuth token response is invalid";
    private static final String AUTHENTICATION_FAILED_WITH_ERROR = "OAuth client_credentials authentication failed with error: %s";

    /**
     * The error codes of RFC 6749 section 5.2.
     */
    private static final Set<String> OAUTH_ERROR_CODES = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "invalid_request", "invalid_client", "invalid_grant", "unauthorized_client", "unsupported_grant_type",
            "invalid_scope")));

    private final URI authorizationUri;
    private final String clientId;
    private final String clientSecret;
    private final Set<OAuthScope> scopes;

    private CloseableHttpClient client;
    private GsonSerializer serializer;

    private OAuthAccessToken accessToken;

    /**
     * Creates the credentials. No token is requested until {@link #initOAuthAccess()} or the first request.
     *
     * @param httpClientBuilder the builder of the client that sends the token requests
     * @param authorizationUri  the OAuth token endpoint: an absolute https URI, or http to a loopback host
     * @param clientId          the OAuth client id
     * @param clientSecret      the OAuth client secret
     * @param scopes            the scopes requested
     * @throws CheckoutArgumentException if a parameter is null or the authorization URI is not accepted
     */
    OAuthSdkCredentials(final HttpClientBuilder httpClientBuilder,
                        final URI authorizationUri,
                        final String clientId,
                        final String clientSecret,
                        final Set<OAuthScope> scopes) {
        super(PlatformType.DEFAULT_OAUTH);
        validateParams("httpClientBuilder", httpClientBuilder, "authorizationUri", authorizationUri,
                "clientId", clientId, "clientSecret", clientSecret, "scopes", scopes);
        this.authorizationUri = validateAuthorizationUri(authorizationUri);
        this.client = httpClientBuilder.build();
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scopes = scopes;
        this.serializer = new GsonSerializer();
    }

    /**
     * Requests the first access token, so a configuration error shows when the SDK is built.
     *
     * @throws CheckoutAuthorizationException if the token request fails or the token response is invalid
     */
    void initOAuthAccess() {
        getAccessToken();
    }

    /**
     * Returns the OAuth authorization for an operation that accepts OAuth, requesting a new token when the cached one
     * has expired.
     *
     * @param authorizationType the authorization type the operation needs
     * @return the authorization with the access token
     * @throws CheckoutAuthorizationException if the operation does not accept OAuth, the token request fails or the
     *                                        token response is invalid
     */
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

    /**
     * Returns the cached token while it is valid, otherwise requests a new one.
     *
     * @return the access token
     * @throws CheckoutAuthorizationException if the token request fails or the token response is invalid
     */
    private synchronized OAuthAccessToken getAccessToken() {
        if (accessToken != null && accessToken.isValid()) {
            log.debug("Using cached OAuth token");
            return accessToken;
        }
        final HttpPost httpPost = new HttpPost(authorizationUri);
        httpPost.setConfig(withoutRedirects(client));
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
        try {
            final List<NameValuePair> data = Arrays.asList(
                    new BasicNameValuePair("grant_type", "client_credentials"),
                    new BasicNameValuePair("client_id", clientId),
                    new BasicNameValuePair("client_secret", clientSecret),
                    new BasicNameValuePair("scope", scopes.stream().map(OAuthScope::getScope).collect(Collectors.joining(" "))));
            httpPost.setEntity(new UrlEncodedFormEntity(data));
        } catch (final UnsupportedEncodingException ignore) {
            throw new CheckoutException("failed to encode oAuth URI");
        }
        log.debug("requesting OAuth token using client_credentials flow");
        try (final CloseableHttpResponse response = client.execute(httpPost)) {
            final int statusCode = response.getStatusLine().getStatusCode();
            final String json = readBody(response);
            final OAuthServiceResponse oAuthServiceResponse = parseResponse(json);

            if (oAuthServiceResponse != null && isOAuthErrorCode(oAuthServiceResponse.error)) {
                throw new CheckoutAuthorizationException(format(AUTHENTICATION_FAILED_WITH_ERROR, oAuthServiceResponse.error));
            }

            if (statusCode != HttpStatus.SC_OK) {
                throw new CheckoutAuthorizationException(TOKEN_REQUEST_FAILED + statusCode);
            }

            final LocalDateTime expirationDate = oAuthServiceResponse == null ? null
                    : expirationDate(oAuthServiceResponse.expiresIn);
            if (oAuthServiceResponse == null
                    || oAuthServiceResponse.error != null
                    || oAuthServiceResponse.accessToken == null
                    || oAuthServiceResponse.accessToken.isEmpty()
                    || expirationDate == null) {
                throw new CheckoutAuthorizationException(INVALID_TOKEN_RESPONSE);
            }

            accessToken = new OAuthAccessToken(
                    oAuthServiceResponse.accessToken,
                    oAuthServiceResponse.tokenType,
                    expirationDate
            );

            log.debug("OAuth token successfully retrieved, expires at: {}", accessToken.getExpirationDate());
            return accessToken;

        } catch (final ClientProtocolException e) {
            throw new CheckoutAuthorizationException(INVALID_TOKEN_RESPONSE);
        } catch (final IOException e) {
            throw new CheckoutAuthorizationException("OAuth client_credentials authentication failed", e);
        }

    }

    /**
     * Reads the token endpoint response body. A body that cannot be read or decoded is an invalid token response.
     */
    private static String readBody(final CloseableHttpResponse response) {
        if (response.getEntity() == null) {
            return "";
        }
        try {
            return EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
        } catch (final IOException | ParseException | IllegalArgumentException e) {
            throw new CheckoutAuthorizationException(INVALID_TOKEN_RESPONSE);
        }
    }

    /**
     * Parses the token endpoint response. A body that is not a JSON object yields {@code null}.
     */
    private OAuthServiceResponse parseResponse(final String json) {
        try {
            return serializer.fromJson(json, OAuthServiceResponse.class);
        } catch (final JsonParseException e) {
            return null;
        }
    }

    /**
     * Whether the value is one of the error codes of RFC 6749 section 5.2.
     */
    private static boolean isOAuthErrorCode(final String error) {
        return error != null && OAUTH_ERROR_CODES.contains(error);
    }

    /**
     * The expiration date of a token that lasts {@code expiresIn} seconds from now, or {@code null} when the value
     * is missing, not positive or too large for a date.
     */
    private static LocalDateTime expirationDate(final Long expiresIn) {
        if (expiresIn == null || expiresIn <= 0) {
            return null;
        }
        try {
            return now().plusSeconds(expiresIn);
        } catch (final DateTimeException | ArithmeticException e) {
            return null;
        }
    }

    /**
     * The request configuration of the client with redirects turned off, so the token request is sent only to the
     * checked authorization URI. A request configuration replaces the client one, so the client one is copied.
     */
    private static RequestConfig withoutRedirects(final CloseableHttpClient client) {
        final RequestConfig clientConfig = client instanceof Configurable ? ((Configurable) client).getConfig() : null;
        return RequestConfig.copy(clientConfig == null ? RequestConfig.DEFAULT : clientConfig)
                .setRedirectsEnabled(false)
                .build();
    }

    /**
     * The token endpoint must be an absolute https URI; http is accepted for loopback hosts only (localhost,
     * 127.0.0.0/8 and ::1), for local mock servers. As for the base URIs, the authority holds visible ASCII
     * characters only, no user information and no "%", and an optional port from 1 to 65535.
     *
     * @param authorizationUri the OAuth token endpoint
     * @return the same URI
     * @throws CheckoutArgumentException if the URI is not https, or http to a host other than loopback, or its
     *                                   authority holds a character that is not visible ASCII, user
     *                                   information, a "%" or a port out of range
     */
    static URI validateAuthorizationUri(final URI authorizationUri) {
        final String scheme = authorizationUri.getScheme();
        final String authority = authorizationUri.getRawAuthority();
        if (authorizationUri.isAbsolute() && !authorizationUri.isOpaque() && scheme != null && authority != null
                && authority.indexOf('@') < 0 && authority.indexOf('%') < 0 && BaseUri.isVisibleAscii(authority)
                && BaseUri.hostOf(authority) != null) {
            if ("https".equalsIgnoreCase(scheme) && URIUtils.extractHost(authorizationUri) != null) {
                return authorizationUri;
            }
            final String host = authorizationUri.getHost();
            if ("http".equalsIgnoreCase(scheme) && host != null && isLoopbackHost(host)) {
                return authorizationUri;
            }
        }
        throw new CheckoutArgumentException(INVALID_AUTHORIZATION_URI);
    }

    /**
     * Whether the host is localhost (any case), [::1] or an IPv4 address in 127.0.0.0/8 written in canonical form.
     */
    private static boolean isLoopbackHost(final String host) {
        if ("localhost".equalsIgnoreCase(host) || "[::1]".equals(host)) {
            return true;
        }
        final String[] parts = host.split("\\.", -1);
        if (parts.length != 4 || !"127".equals(parts[0])) {
            return false;
        }
        for (final String part : parts) {
            if (!part.matches("0|[1-9][0-9]{0,2}") || Integer.parseInt(part) > 255) {
                return false;
            }
        }
        return true;
    }

    /**
     * Replaces the cached access token (tests).
     *
     * @param accessToken the token to cache
     */
    synchronized void setAccessToken(final OAuthAccessToken accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Replaces the client that sends the token requests (tests).
     *
     * @param client the client
     */
    void setClient(final CloseableHttpClient client) {
        this.client = client;
    }

    /**
     * Replaces the serializer of the token responses (tests).
     *
     * @param serializer the serializer
     */
    void setSerializer(final GsonSerializer serializer) {
        this.serializer = serializer;
    }

    /**
     * The cached access token, or {@code null} before the first token request.
     *
     * @return the cached token
     */
    OAuthAccessToken getOAuthAccessToken() {
        return accessToken;
    }

    /**
     * The token endpoint response ({@code POST /connect/token}).
     */
    private static class OAuthServiceResponse {

        /**
         * The access token. [Optional]
         */
        @SerializedName("access_token")
        String accessToken;

        /**
         * The token type. [Optional]
         */
        @SerializedName("token_type")
        String tokenType;

        /**
         * The token lifetime in seconds. [Optional]
         */
        @SerializedName("expires_in")
        Long expiresIn;

        /**
         * The OAuth error code of a failed request. [Optional]
         * Enum: "invalid_request" "invalid_client" "invalid_grant" "unauthorized_client" "unsupported_grant_type"
         * "invalid_scope"
         */
        String error;

    }

}

