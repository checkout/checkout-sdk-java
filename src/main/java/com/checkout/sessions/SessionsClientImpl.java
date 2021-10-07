package com.checkout.sessions;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.Resource;
import com.checkout.sessions.channel.ChannelData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static com.checkout.common.CheckoutUtils.validateParams;

public class SessionsClientImpl extends AbstractClient implements SessionsClient {

    private static final String SESSIONS = "sessions";
    private static final String COLLECT_DATA = "collect-data";
    private static final String COMPLETE = "complete";
    private static final String ISSUER_FINGERPRINT = "issuer-fingerprint";
    private static final String SESSION_ID = "sessionId";

    private static final Map<Integer, Class<? extends Resource>> SESSION_RESPONSE_MAPPINGS = new HashMap<>();

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
    public CompletableFuture<Void> completeSession(final String sessionId) {
        return completeSession(sessionId, sdkAuthorization());
    }

    @Override
    public CompletableFuture<Void> completeSession(final String sessionSecret, final String sessionId) {
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
        return apiClient.postAsync(SESSIONS, sdkAuthorization(), SESSION_RESPONSE_MAPPINGS, sessionRequest, null)
                .thenApply((Function<Resource, SessionResponse>) resource -> {
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
        return apiClient.getAsync(buildPath(SESSIONS, sessionId), sdkAuthorization, GetSessionResponse.class);
    }

    private CompletableFuture<GetSessionResponse> updateSession(final String sessionId,
                                                                final ChannelData channelData,
                                                                final SdkAuthorization sdkAuthorization) {
        validateParams(SESSION_ID, sessionId, "channelData", channelData);
        return apiClient.putAsync(buildPath(SESSIONS, sessionId, COLLECT_DATA), sdkAuthorization, GetSessionResponse.class, channelData);
    }

    private CompletableFuture<Void> completeSession(final String sessionId,
                                                    final SdkAuthorization sdkAuthorization) {
        validateParams(SESSION_ID, sessionId);
        return apiClient.postAsync(buildPath(SESSIONS, sessionId, COMPLETE), sdkAuthorization, Void.class, null, null);
    }

    private CompletableFuture<GetSessionResponseAfterChannelDataSupplied> update3dsMethodCompletionIndicator(final String sessionId,
                                                                                                             final ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest,
                                                                                                             final SdkAuthorization sdkAuthorization) {
        validateParams(SESSION_ID, sessionId, "threeDsMethodCompletionRequest", threeDsMethodCompletionRequest);
        return apiClient.putAsync(buildPath(SESSIONS, sessionId, ISSUER_FINGERPRINT), sdkAuthorization, GetSessionResponseAfterChannelDataSupplied.class, threeDsMethodCompletionRequest);
    }

    private SdkAuthorization sessionSecretAuthorization(final String sessionSecret) {
        validateParams("sessionSecret", sessionSecret);
        return new SessionSecretSdkCredentials(sessionSecret).getAuthorization(SdkAuthorizationType.CUSTOM);
    }

}
