package com.checkout.sessions;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.HttpMetadata;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.sessions.channel.ChannelData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.checkout.common.CheckoutUtils.validateParams;

public class SessionsClientImpl extends AbstractClient implements SessionsClient {

    private static final String SESSIONS_PATH = "sessions";
    private static final String COLLECT_DATA_PATH = "collect-data";
    private static final String COMPLETE_PATH = "complete";
    private static final String ISSUER_FINGERPRINT_PATH = "issuer-fingerprint";
    private static final String SESSION_ID = "sessionId";

    private static final Map<Integer, Class<? extends HttpMetadata>> SESSION_RESPONSE_MAPPINGS = new HashMap<>();

    static {
        SESSION_RESPONSE_MAPPINGS.put(201, CreateSessionOkResponse.class);
        SESSION_RESPONSE_MAPPINGS.put(202, CreateSessionAcceptedResponse.class);
    }

    public SessionsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.OAUTH);
    }

    @Override
    public CompletableFuture<SessionResponse> requestSession(final SessionRequest sessionRequest) {
        validateSessionRequest(sessionRequest);
        return apiClient.postAsync(SESSIONS_PATH, resolveAuthorization(), SESSION_RESPONSE_MAPPINGS, sessionRequest, null)
                .thenApply((Function<HttpMetadata, SessionResponse>) resource -> {
                    if (resource instanceof CreateSessionOkResponse) {
                        return new SessionResponse((CreateSessionOkResponse) resource);
                    } else if (resource instanceof CreateSessionAcceptedResponse) {
                        return new SessionResponse((CreateSessionAcceptedResponse) resource);
                    } else {
                        throw new IllegalStateException("Unexpected mapping type " + resource.getClass());
                    }
                });
    }

    @Override
    public CompletableFuture<GetSessionResponse> getSessionDetails(final String sessionId) {
        validateSessionId(sessionId);
        return apiClient.getAsync(buildPath(SESSIONS_PATH, sessionId), resolveAuthorization(), GetSessionResponse.class);
    }

    @Override
    public CompletableFuture<GetSessionResponse> getSessionDetails(final String sessionSecret, final String sessionId) {
        validateSessionId(sessionId);
        return apiClient.getAsync(buildPath(SESSIONS_PATH, sessionId), resolveAuthorization(sessionSecret), GetSessionResponse.class);
    }

    @Override
    public final CompletableFuture<GetSessionResponse> updateSession(final String sessionId, final ChannelData channelData) {
        validateSessionIdAndChannelData(sessionId, channelData);
        return apiClient.putAsync(buildPath(SESSIONS_PATH, sessionId, COLLECT_DATA_PATH), resolveAuthorization(), GetSessionResponse.class, channelData);
    }

    @Override
    public CompletableFuture<GetSessionResponse> updateSession(final String sessionSecret,
                                                               final String sessionId,
                                                               final ChannelData channelData) {
        validateSessionIdAndChannelData(sessionId, channelData);
        return apiClient.putAsync(buildPath(SESSIONS_PATH, sessionId, COLLECT_DATA_PATH), resolveAuthorization(sessionSecret), GetSessionResponse.class, channelData);
    }

    @Override
    public CompletableFuture<EmptyResponse> completeSession(final String sessionId) {
        validateSessionId(sessionId);
        return apiClient.postAsync(buildPath(SESSIONS_PATH, sessionId, COMPLETE_PATH), resolveAuthorization(), EmptyResponse.class, null, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> completeSession(final String sessionSecret, final String sessionId) {
        validateSessionId(sessionId);
        return apiClient.postAsync(buildPath(SESSIONS_PATH, sessionId, COMPLETE_PATH), resolveAuthorization(sessionSecret), EmptyResponse.class, null, null);
    }

    @Override
    public CompletableFuture<GetSessionResponseAfterChannelDataSupplied> update3dsMethodCompletionIndicator(final String sessionId,
                                                                                                            final ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest) {
        validateSessionIdAndThreeDsRequest(sessionId, threeDsMethodCompletionRequest);
        return apiClient.putAsync(buildPath(SESSIONS_PATH, sessionId, ISSUER_FINGERPRINT_PATH), resolveAuthorization(), GetSessionResponseAfterChannelDataSupplied.class, threeDsMethodCompletionRequest);
    }

    @Override
    public CompletableFuture<GetSessionResponseAfterChannelDataSupplied> update3dsMethodCompletionIndicator(final String sessionSecret,
                                                                                                            final String sessionId,
                                                                                                            final ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest) {
        validateSessionIdAndThreeDsRequest(sessionId, threeDsMethodCompletionRequest);
        return apiClient.putAsync(buildPath(SESSIONS_PATH, sessionId, ISSUER_FINGERPRINT_PATH), resolveAuthorization(sessionSecret), GetSessionResponseAfterChannelDataSupplied.class, threeDsMethodCompletionRequest);
    }

    // Synchronous methods
    @Override
    public SessionResponse requestSessionSync(final SessionRequest sessionRequest) {
        validateSessionRequest(sessionRequest);
        final HttpMetadata resource = apiClient.post(SESSIONS_PATH, resolveAuthorization(), SESSION_RESPONSE_MAPPINGS, sessionRequest, null);
        if (resource instanceof CreateSessionOkResponse) {
            return new SessionResponse((CreateSessionOkResponse) resource);
        } else if (resource instanceof CreateSessionAcceptedResponse) {
            return new SessionResponse((CreateSessionAcceptedResponse) resource);
        } else {
            throw new IllegalStateException("Unexpected mapping type " + resource.getClass());
        }
    }

    @Override
    public GetSessionResponse getSessionDetailsSync(final String sessionId) {
        validateSessionId(sessionId);
        return apiClient.get(buildPath(SESSIONS_PATH, sessionId), resolveAuthorization(), GetSessionResponse.class);
    }

    @Override
    public GetSessionResponse getSessionDetailsSync(final String sessionSecret, final String sessionId) {
        validateSessionId(sessionId);
        return apiClient.get(buildPath(SESSIONS_PATH, sessionId), resolveAuthorization(sessionSecret), GetSessionResponse.class);
    }

    @Override
    public GetSessionResponse updateSessionSync(final String sessionId, final ChannelData channelData) {
        validateSessionIdAndChannelData(sessionId, channelData);
        return apiClient.put(buildPath(SESSIONS_PATH, sessionId, COLLECT_DATA_PATH), resolveAuthorization(), GetSessionResponse.class, channelData);
    }

    @Override
    public GetSessionResponse updateSessionSync(final String sessionSecret, final String sessionId, final ChannelData channelData) {
        validateSessionIdAndChannelData(sessionId, channelData);
        return apiClient.put(buildPath(SESSIONS_PATH, sessionId, COLLECT_DATA_PATH), resolveAuthorization(sessionSecret), GetSessionResponse.class, channelData);
    }

    @Override
    public EmptyResponse completeSessionSync(final String sessionId) {
        validateSessionId(sessionId);
        return apiClient.post(buildPath(SESSIONS_PATH, sessionId, COMPLETE_PATH), resolveAuthorization(), EmptyResponse.class, null, null);
    }

    @Override
    public EmptyResponse completeSessionSync(final String sessionSecret, final String sessionId) {
        validateSessionId(sessionId);
        return apiClient.post(buildPath(SESSIONS_PATH, sessionId, COMPLETE_PATH), resolveAuthorization(sessionSecret), EmptyResponse.class, null, null);
    }

    @Override
    public GetSessionResponseAfterChannelDataSupplied update3dsMethodCompletionIndicatorSync(final String sessionId, final ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest) {
        validateSessionIdAndThreeDsRequest(sessionId, threeDsMethodCompletionRequest);
        return apiClient.put(buildPath(SESSIONS_PATH, sessionId, ISSUER_FINGERPRINT_PATH), resolveAuthorization(), GetSessionResponseAfterChannelDataSupplied.class, threeDsMethodCompletionRequest);
    }

    @Override
    public GetSessionResponseAfterChannelDataSupplied update3dsMethodCompletionIndicatorSync(final String sessionSecret, final String sessionId, final ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest) {
        validateSessionIdAndThreeDsRequest(sessionId, threeDsMethodCompletionRequest);
        return apiClient.put(buildPath(SESSIONS_PATH, sessionId, ISSUER_FINGERPRINT_PATH), resolveAuthorization(sessionSecret), GetSessionResponseAfterChannelDataSupplied.class, threeDsMethodCompletionRequest);
    }

    // Common methods
    protected void validateSessionRequest(final SessionRequest sessionRequest) {
        validateParams("sessionRequest", sessionRequest);
    }

    protected void validateSessionId(final String sessionId) {
        validateParams(SESSION_ID, sessionId);
    }

    protected void validateSessionIdAndChannelData(final String sessionId, final ChannelData channelData) {
        validateParams(SESSION_ID, sessionId, "channelData", channelData);
    }


    protected void validateSessionIdAndThreeDsRequest(final String sessionId, final ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest) {
        validateParams(SESSION_ID, sessionId, "threeDsMethodCompletionRequest", threeDsMethodCompletionRequest);
    }

    // Authorization resolver methods
    private SdkAuthorization resolveAuthorization() {
        return sdkAuthorization();
    }

    private SdkAuthorization resolveAuthorization(final String sessionSecret) {
        validateParams("sessionSecret", sessionSecret);
        return new SessionSecretSdkCredentials(sessionSecret).getAuthorization(SdkAuthorizationType.CUSTOM);
    }

}
