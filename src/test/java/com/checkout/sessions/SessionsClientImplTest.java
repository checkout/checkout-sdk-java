package com.checkout.sessions;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.HttpMetadata;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.sessions.channel.AppSession;
import com.checkout.sessions.channel.ChannelData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionsClientImplTest {

    private static final SdkAuthorization SESSION_SECRET_AUTHORIZATION = new SessionSecretSdkCredentials("sessionSecret")
            .getAuthorization(SdkAuthorizationType.CUSTOM);

    private static final Map<Integer, Class<? extends HttpMetadata>> SESSION_RESPONSE_MAPPINGS = new HashMap<>();

    static {
        SESSION_RESPONSE_MAPPINGS.put(201, CreateSessionOkResponse.class);
        SESSION_RESPONSE_MAPPINGS.put(202, CreateSessionAcceptedResponse.class);
    }

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private SessionsClient sessionsClient;

    @BeforeEach
    void setup() {
        this.sessionsClient = new SessionsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRequestSession_CreateSessionOkResponse() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final SessionRequest request = mock(SessionRequest.class);
        final CreateSessionOkResponse response = mock(CreateSessionOkResponse.class);

        doReturn(CompletableFuture.completedFuture(response))
                .when(apiClient)
                .postAsync(eq("sessions"), eq(authorization), eq(SESSION_RESPONSE_MAPPINGS), eq(request), isNull());

        final CompletableFuture<SessionResponse> getSessionResponse = sessionsClient.requestSession(request);

        assertNotNull(getSessionResponse.get());
        assertNotNull(getSessionResponse.get().getCreated());
        assertNull(getSessionResponse.get().getAccepted());

    }

    @Test
    void shouldRequestSession_CreateSessionAcceptedResponse() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final SessionRequest request = mock(SessionRequest.class);
        final CreateSessionAcceptedResponse response = mock(CreateSessionAcceptedResponse.class);

        doReturn(CompletableFuture.completedFuture(response))
                .when(apiClient)
                .postAsync(eq("sessions"), eq(authorization), eq(SESSION_RESPONSE_MAPPINGS), eq(request), isNull());

        final CompletableFuture<SessionResponse> getSessionResponse = sessionsClient.requestSession(request);

        assertNotNull(getSessionResponse.get());
        assertNotNull(getSessionResponse.get().getAccepted());
        assertNull(getSessionResponse.get().getCreated());

    }

    @Test
    void requestSession_shouldThrowOnNullRequest() {

        try {
            sessionsClient.requestSession(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("sessionRequest cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void shouldGetSessionDetails() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final GetSessionResponse response = mock(GetSessionResponse.class);

        doReturn(CompletableFuture.completedFuture(response))
                .when(apiClient)
                .getAsync("sessions/id", authorization, GetSessionResponse.class);

        final CompletableFuture<GetSessionResponse> getSessionResponse = sessionsClient.getSessionDetails("id");

        assertNotNull(getSessionResponse.get());

    }

    @Test
    void shouldGetSessionDetails_sessionSecret() throws ExecutionException, InterruptedException {

        final GetSessionResponse response = mock(GetSessionResponse.class);

        doReturn(CompletableFuture.completedFuture(response))
                .when(apiClient)
                .getAsync("sessions/id", SESSION_SECRET_AUTHORIZATION, GetSessionResponse.class);

        final CompletableFuture<GetSessionResponse> getSessionResponse = sessionsClient.getSessionDetails("sessionSecret", "id");

        assertNotNull(getSessionResponse.get());

    }

    @Test
    void getSessionDetails_shouldThrowOnNullRequest() {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        try {
            sessionsClient.getSessionDetails(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("sessionId cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void getSessionDetails_shouldThrowOnSessionSecret() {

        try {
            sessionsClient.getSessionDetails(null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("sessionSecret cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void shouldUpdateSessionDetails() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final ChannelData request = mock(ChannelData.class);
        final GetSessionResponse response = mock(GetSessionResponse.class);

        doReturn(CompletableFuture.completedFuture(response))
                .when(apiClient)
                .putAsync("sessions/id/collect-data", authorization, GetSessionResponse.class, request);

        final CompletableFuture<GetSessionResponse> getSessionResponse = sessionsClient.updateSession("id", request);

        assertNotNull(getSessionResponse.get());

    }

    @Test
    void shouldUpdateSessionDetails_sessionSecret() throws ExecutionException, InterruptedException {

        final ChannelData request = mock(ChannelData.class);
        final GetSessionResponse response = mock(GetSessionResponse.class);

        doReturn(CompletableFuture.completedFuture(response))
                .when(apiClient)
                .putAsync("sessions/id/collect-data", SESSION_SECRET_AUTHORIZATION, GetSessionResponse.class, request);

        final CompletableFuture<GetSessionResponse> getSessionResponse = sessionsClient.updateSession("sessionSecret", "id", request);

        assertNotNull(getSessionResponse.get());

    }

    @Test
    void updateSession_shouldThrowOnNullRequest() {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        try {
            sessionsClient.updateSession(null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("sessionId cannot be null", e.getMessage());
        }

        try {
            sessionsClient.updateSession("id", null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("channelData cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void updateSession_shouldThrowOnNullRequest_sessionSecret() {

        try {
            sessionsClient.updateSession("secret", null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("sessionId cannot be null", e.getMessage());
        }

        try {
            sessionsClient.updateSession("secret", "id", null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("channelData cannot be null", e.getMessage());
        }

        try {
            sessionsClient.updateSession(null, "id", AppSession.builder().build());
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("sessionSecret cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void shouldCompleteSession() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final GetSessionResponse response = mock(GetSessionResponse.class);

        doReturn(CompletableFuture.completedFuture(response))
                .when(apiClient)
                .postAsync(eq("sessions/id/complete"), eq(authorization), eq(EmptyResponse.class), isNull(), isNull());

        final CompletableFuture<EmptyResponse> completeSessionResponse = sessionsClient.completeSession("id");

        assertNotNull(completeSessionResponse.get());

    }

    @Test
    void shouldCompleteSession_sessionSecret() throws ExecutionException, InterruptedException {

        final EmptyResponse response = mock(EmptyResponse.class);

        doReturn(CompletableFuture.completedFuture(response))
                .when(apiClient)
                .postAsync(eq("sessions/id/complete"), eq(SESSION_SECRET_AUTHORIZATION), eq(EmptyResponse.class), isNull(), isNull());

        final CompletableFuture<EmptyResponse> completeSessionResponse = sessionsClient.completeSession("sessionSecret", "id");

        assertNotNull(completeSessionResponse.get());

    }

    @Test
    void completeSession_shouldThrowOnNullRequest() {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        try {
            sessionsClient.completeSession(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("sessionId cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void completeSession_shouldThrowOnNullRequest_sessionSecret() {

        try {
            sessionsClient.completeSession(null, "sessionSecret");
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("sessionSecret cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void shouldUpdate3dsMethodCompletionIndicator() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final ThreeDsMethodCompletionRequest request = mock(ThreeDsMethodCompletionRequest.class);
        final GetSessionResponseAfterChannelDataSupplied response = mock(GetSessionResponseAfterChannelDataSupplied.class);

        when(apiClient.putAsync("sessions/id/issuer-fingerprint", authorization, GetSessionResponseAfterChannelDataSupplied.class, request))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GetSessionResponseAfterChannelDataSupplied> getSessionResponse = sessionsClient.update3dsMethodCompletionIndicator("id", request);

        assertNotNull(getSessionResponse.get());

    }

    @Test
    void shouldUpdate3dsMethodCompletionIndicator_sessionSecret() throws ExecutionException, InterruptedException {

        final ThreeDsMethodCompletionRequest request = mock(ThreeDsMethodCompletionRequest.class);
        final GetSessionResponseAfterChannelDataSupplied response = mock(GetSessionResponseAfterChannelDataSupplied.class);

        when(apiClient.putAsync("sessions/id/issuer-fingerprint", SESSION_SECRET_AUTHORIZATION, GetSessionResponseAfterChannelDataSupplied.class, request))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GetSessionResponseAfterChannelDataSupplied> getSessionResponse = sessionsClient.update3dsMethodCompletionIndicator("sessionSecret", "id", request);

        assertNotNull(getSessionResponse.get());

    }

    @Test
    void update3dsMethodCompletionIndicator_shouldThrowOnNullRequest() {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        try {
            sessionsClient.update3dsMethodCompletionIndicator(null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("sessionId cannot be null", e.getMessage());
        }

        try {
            sessionsClient.update3dsMethodCompletionIndicator("id", null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("threeDsMethodCompletionRequest cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void update3dsMethodCompletionIndicator_shouldThrowOnNullRequest_sessionSecret() {

        try {
            sessionsClient.update3dsMethodCompletionIndicator("sessionSecret", null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("sessionId cannot be null", e.getMessage());
        }

        try {
            sessionsClient.update3dsMethodCompletionIndicator("sessionSecret", "id", null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("threeDsMethodCompletionRequest cannot be null", e.getMessage());
        }

        try {
            sessionsClient.update3dsMethodCompletionIndicator(null, "id", ThreeDsMethodCompletionRequest.builder().build());
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("sessionSecret cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

}
