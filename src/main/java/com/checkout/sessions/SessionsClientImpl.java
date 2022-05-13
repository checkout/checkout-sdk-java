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
        validateParams("sessionRequest", sessionRequest);
        return createSession(sessionRequest);
    }

    @Override
    public CompletableFuture<GetSessionResponse> getSessionDetails(final String sessionId) {
        return getSessionDetails(sessionId, sdkAuthorization());
    }

    @Override
    public CompletableFuture<GetSessionResponse> getSessionDetails(final String sessionSecret, final String sessionId) {
        return getSessionDetails(sessionId, sessionSecretAuthorization(sessionSecret));
    }

    @Override
    public final CompletableFuture<GetSessionResponse> updateSession(final String sessionId, final ChannelData channelData) {
        return updateSession(sessionId, channelData, sdkAuthorization());
    }

    @Override
    public CompletableFuture<GetSessionResponse> updateSession(final String sessionSecret,
                                                               final String sessionId,
                                                               final ChannelData channelData) {
        return updateSession(sessionId, channelData, sessionSecretAuthorization(sessionSecret));
    }

    @Override
    public CompletableFuture<EmptyResponse> completeSession(final String sessionId) {
        return completeSession(sessionId, sdkAuthorization());
    }

    @Override
    public CompletableFuture<EmptyResponse> completeSession(final String sessionSecret, final String sessionId) {
        return completeSession(sessionId, sessionSecretAuthorization(sessionSecret));
    }

    @Override
    public CompletableFuture<GetSessionResponseAfterChannelDataSupplied> update3dsMethodCompletionIndicator(final String sessionId,
                                                                                                            final ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest) {
        return update3dsMethodCompletionIndicator(sessionId, threeDsMethodCompletionRequest, sdkAuthorization());
    }

    @Override
    public CompletableFuture<GetSessionResponseAfterChannelDataSupplied> update3dsMethodCompletionIndicator(final String sessionSecret,
                                                                                                            final String sessionId,
                                                                                                            final ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest) {
        return update3dsMethodCompletionIndicator(sessionId, threeDsMethodCompletionRequest, sessionSecretAuthorization(sessionSecret));
    }

    private CompletableFuture<SessionResponse> createSession(final SessionRequest sessionRequest) {
        return apiClient.postAsync(SESSIONS_PATH, sdkAuthorization(), SESSION_RESPONSE_MAPPINGS, sessionRequest, null)
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

    private CompletableFuture<GetSessionResponse> getSessionDetails(final String sessionId,
                                                                    final SdkAuthorization sdkAuthorization) {
        validateParams(SESSION_ID, sessionId);
        return apiClient.getAsync(buildPath(SESSIONS_PATH, sessionId), sdkAuthorization, GetSessionResponse.class);
    }

    private CompletableFuture<GetSessionResponse> updateSession(final String sessionId,
                                                                final ChannelData channelData,
                                                                final SdkAuthorization sdkAuthorization) {
        validateParams(SESSION_ID, sessionId, "channelData", channelData);
        return apiClient.putAsync(buildPath(SESSIONS_PATH, sessionId, COLLECT_DATA_PATH), sdkAuthorization, GetSessionResponse.class, channelData);
    }

    private CompletableFuture<EmptyResponse> completeSession(final String sessionId,
                                                             final SdkAuthorization sdkAuthorization) {
        validateParams(SESSION_ID, sessionId);
        return apiClient.postAsync(buildPath(SESSIONS_PATH, sessionId, COMPLETE_PATH), sdkAuthorization, EmptyResponse.class, null, null);
    }

    private CompletableFuture<GetSessionResponseAfterChannelDataSupplied> update3dsMethodCompletionIndicator(final String sessionId,
                                                                                                             final ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest,
                                                                                                             final SdkAuthorization sdkAuthorization) {
        validateParams(SESSION_ID, sessionId, "threeDsMethodCompletionRequest", threeDsMethodCompletionRequest);
        return apiClient.putAsync(buildPath(SESSIONS_PATH, sessionId, ISSUER_FINGERPRINT_PATH), sdkAuthorization, GetSessionResponseAfterChannelDataSupplied.class, threeDsMethodCompletionRequest);
    }

    private SdkAuthorization sessionSecretAuthorization(final String sessionSecret) {
        validateParams("sessionSecret", sessionSecret);
        return new SessionSecretSdkCredentials(sessionSecret).getAuthorization(SdkAuthorizationType.CUSTOM);
    }

}
