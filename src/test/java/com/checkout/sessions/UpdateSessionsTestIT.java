package com.checkout.sessions;

import com.checkout.sessions.channel.ThreeDsMethodCompletion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdateSessionsTestIT extends AbstractSessionsTestIT {

    private static Stream<Arguments> authType() {
        return Stream.of(
                Arguments.of(false),
                Arguments.of(true)
        );
    }

    @ParameterizedTest
    @MethodSource("authType")
    void shouldUpdateCardSession_browserSession(final boolean usingSessionSecret) {

        final SessionResponse createSessionResponse = createHostedSession();
        final CreateSessionAcceptedResponse created = validateAndExtractCreatedResponse(createSessionResponse);

        final GetSessionResponse updated;

        if (!usingSessionSecret) {
            updated = blocking(() -> checkoutApi.sessionsClient().updateSession(created.getId(), browserSession()));
        } else {
            updated = blocking(() -> checkoutApi.sessionsClient().updateSession(created.getSessionSecret(), created.getId(), browserSession()));
        }

        validateUpdatedSession(updated, usingSessionSecret);
    }

    @Test
    void shouldUpdateCardSession_appSession() {

        final SessionResponse createSessionResponse = createHostedSession();
        final CreateSessionAcceptedResponse created = validateAndExtractCreatedResponse(createSessionResponse);

        final GetSessionResponse updated = blocking(() -> checkoutApi.sessionsClient().updateSession(created.getId(), appSession()));

        validateAppUpdatedSession(updated);
    }

    @ParameterizedTest
    @MethodSource("authType")
    void shouldUpdate3dsMethodCompletionIndicator(final boolean usingSessionSecret) {

        final SessionResponse createSessionResponse = createHostedSession();
        final CreateSessionAcceptedResponse created = validateAndExtractCreatedResponse(createSessionResponse);

        final ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest = createThreeDsMethodCompletionRequest();

        final GetSessionResponseAfterChannelDataSupplied updated;

        if (usingSessionSecret) {
            updated = blocking(() -> checkoutApi.sessionsClient().update3dsMethodCompletionIndicator(created.getId(), threeDsMethodCompletionRequest));
        } else {
            updated = blocking(() -> checkoutApi.sessionsClient().update3dsMethodCompletionIndicator(created.getSessionSecret(), created.getId(), threeDsMethodCompletionRequest));
        }

        validate3dsMethodUpdatedSession(updated);
    }

    // Synchronous method tests
    @ParameterizedTest
    @MethodSource("authType")
    void shouldUpdateCardSessionSync_browserSession(final boolean usingSessionSecret) {

        final SessionResponse createSessionResponse = createHostedSession();
        final CreateSessionAcceptedResponse created = validateAndExtractCreatedResponse(createSessionResponse);

        final GetSessionResponse updated;

        if (!usingSessionSecret) {
            updated = checkoutApi.sessionsClient().updateSessionSync(created.getId(), browserSession());
        } else {
            updated = checkoutApi.sessionsClient().updateSessionSync(created.getSessionSecret(), created.getId(), browserSession());
        }

        validateUpdatedSession(updated, usingSessionSecret);
    }

    @Test
    void shouldUpdateCardSessionSync_appSession() {

        final SessionResponse createSessionResponse = createHostedSession();
        final CreateSessionAcceptedResponse created = validateAndExtractCreatedResponse(createSessionResponse);

        final GetSessionResponse updated = checkoutApi.sessionsClient().updateSessionSync(created.getId(), appSession());

        validateAppUpdatedSession(updated);
    }

    @ParameterizedTest
    @MethodSource("authType")
    void shouldUpdate3dsMethodCompletionIndicatorSync(final boolean usingSessionSecret) {

        final SessionResponse createSessionResponse = createHostedSession();
        final CreateSessionAcceptedResponse created = validateAndExtractCreatedResponse(createSessionResponse);

        final ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest = createThreeDsMethodCompletionRequest();

        final GetSessionResponseAfterChannelDataSupplied updated;

        if (usingSessionSecret) {
            updated = checkoutApi.sessionsClient().update3dsMethodCompletionIndicatorSync(created.getId(), threeDsMethodCompletionRequest);
        } else {
            updated = checkoutApi.sessionsClient().update3dsMethodCompletionIndicatorSync(created.getSessionSecret(), created.getId(), threeDsMethodCompletionRequest);
        }

        validate3dsMethodUpdatedSession(updated);
    }

    // Common methods
    private CreateSessionAcceptedResponse validateAndExtractCreatedResponse(SessionResponse createSessionResponse) {
        assertNotNull(createSessionResponse);
        assertNotNull(createSessionResponse.getAccepted());

        final CreateSessionAcceptedResponse created = createSessionResponse.getAccepted();
        assertNotNull(created.getId());
        assertNotNull(created.getSessionSecret());
        assertNotNull(created.getTransactionId());
        assertNotNull(created.getAmount());
        assertNotNull(created.getCard());
        assertEquals(AuthenticationType.REGULAR, created.getAuthenticationType());
        assertEquals(Category.PAYMENT, created.getAuthenticationCategory());
        assertEquals(SessionStatus.PENDING, created.getStatus());
        assertEquals(1, created.getNextActions().size());
        assertEquals(NextAction.REDIRECT_CARDHOLDER, created.getNextActions().get(0));
        assertNotNull(created.getSelfLink());
        assertNotNull(created.getLink("success_url"));
        assertNotNull(created.getLink("failure_url"));
        assertNotNull(created.getLink("redirect_url"));

        return created;
    }

    private void validateUpdatedSession(GetSessionResponse updated, boolean usingSessionSecret) {
        assertNotNull(updated);
        assertNotNull(updated.getId());

        if (usingSessionSecret) {
            assertNull(updated.getSessionSecret());
        } else {
            assertNotNull(updated.getSessionSecret());
        }

        assertNotNull(updated.getTransactionId());
        assertNotNull(updated.getAmount());
        assertNotNull(updated.getCard());
        assertEquals(AuthenticationType.REGULAR, updated.getAuthenticationType());
        assertEquals(Category.PAYMENT, updated.getAuthenticationCategory());
        assertEquals(SessionStatus.APPROVED, updated.getStatus());
        assertTrue(updated.getNextActions().isEmpty());
        assertNotNull(updated.getSelfLink());
        assertNotNull(updated.getLink("success_url"));
        assertNotNull(updated.getLink("failure_url"));
        assertNull(updated.getLink("redirect_url"));
    }

    private void validateAppUpdatedSession(GetSessionResponse updated) {
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertNotNull(updated.getTransactionId());
        assertNotNull(updated.getAmount());
        assertNotNull(updated.getCard());
        assertEquals(AuthenticationType.REGULAR, updated.getAuthenticationType());
        assertEquals(Category.PAYMENT, updated.getAuthenticationCategory());
        assertEquals(SessionStatus.UNAVAILABLE, updated.getStatus());
        assertNotNull(updated.getSelfLink());
        assertNotNull(updated.getLink("success_url"));
        assertNotNull(updated.getLink("failure_url"));
    }

    private ThreeDsMethodCompletionRequest createThreeDsMethodCompletionRequest() {
        return ThreeDsMethodCompletionRequest.builder()
                .threeDsMethodCompletion(ThreeDsMethodCompletion.Y)
                .build();
    }

    private void validate3dsMethodUpdatedSession(GetSessionResponseAfterChannelDataSupplied updated) {
        assertNotNull(updated);
        assertNotNull(updated.getId());
        assertNotNull(updated.getTransactionId());
        assertNotNull(updated.getAmount());
        assertNotNull(updated.getCard());
        assertEquals(AuthenticationType.REGULAR, updated.getAuthenticationType());
        assertEquals(Category.PAYMENT, updated.getAuthenticationCategory());
        assertEquals(SessionStatus.PENDING, updated.getStatus());
    }

}
