package com.checkout.sessions;


import com.checkout.sessions.channel.ChannelData;

import java.util.concurrent.CompletableFuture;

public interface SessionsClient {

    CompletableFuture<SessionResponse> requestSession(SessionRequest sessionRequest);

    CompletableFuture<GetSessionResponse> getSessionDetails(String sessionId);

    CompletableFuture<GetSessionResponse> getSessionDetails(String sessionSecret, String sessionId);

    CompletableFuture<GetSessionResponse> updateSession(String sessionId, ChannelData channelData);

    CompletableFuture<GetSessionResponse> updateSession(String sessionSecret, String sessionId, ChannelData channelData);

    CompletableFuture<Void> completeSession(String sessionId);

    CompletableFuture<Void> completeSession(String sessionSecret, String sessionId);

    CompletableFuture<GetSessionResponseAfterChannelDataSupplied> update3dsMethodCompletionIndicator(String sessionId, ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest);

    CompletableFuture<GetSessionResponseAfterChannelDataSupplied> update3dsMethodCompletionIndicator(String sessionSecret, String sessionId, ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest);

}
