package com.checkout.sessions;


import com.checkout.EmptyResponse;
import com.checkout.sessions.channel.ChannelData;

import java.util.concurrent.CompletableFuture;

public interface SessionsClient {

    CompletableFuture<SessionResponse> requestSession(SessionRequest sessionRequest);

    CompletableFuture<GetSessionResponse> getSessionDetails(String sessionId);

    CompletableFuture<GetSessionResponse> getSessionDetails(String sessionSecret, String sessionId);

    CompletableFuture<GetSessionResponse> updateSession(String sessionId, ChannelData channelData);

    CompletableFuture<GetSessionResponse> updateSession(String sessionSecret, String sessionId, ChannelData channelData);

    CompletableFuture<EmptyResponse> completeSession(String sessionId);

    CompletableFuture<EmptyResponse> completeSession(String sessionSecret, String sessionId);

    CompletableFuture<GetSessionResponseAfterChannelDataSupplied> update3dsMethodCompletionIndicator(String sessionId, ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest);

    CompletableFuture<GetSessionResponseAfterChannelDataSupplied> update3dsMethodCompletionIndicator(String sessionSecret, String sessionId, ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest);

    // Synchronous methods
    SessionResponse requestSessionSync(SessionRequest sessionRequest);

    GetSessionResponse getSessionDetailsSync(String sessionId);

    GetSessionResponse getSessionDetailsSync(String sessionSecret, String sessionId);

    GetSessionResponse updateSessionSync(String sessionId, ChannelData channelData);

    GetSessionResponse updateSessionSync(String sessionSecret, String sessionId, ChannelData channelData);

    EmptyResponse completeSessionSync(String sessionId);

    EmptyResponse completeSessionSync(String sessionSecret, String sessionId);

    GetSessionResponseAfterChannelDataSupplied update3dsMethodCompletionIndicatorSync(String sessionId, ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest);

    GetSessionResponseAfterChannelDataSupplied update3dsMethodCompletionIndicatorSync(String sessionSecret, String sessionId, ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest);

}
