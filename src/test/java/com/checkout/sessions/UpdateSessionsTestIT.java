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
    void shouldUpdateCardSession(final boolean usingSessionSecret) {

        final SessionResponse createSessionResponse = createHostedSession();

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

        final GetSessionResponse updated;

        if (!usingSessionSecret) {
            updated = blocking(() -> fourApi.sessionsClient().updateSession(created.getId(), browserSession()));
        } else {
            updated = blocking(() -> fourApi.sessionsClient().updateSession(created.getSessionSecret(), created.getId(), browserSession()));
        }

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

    @Test
    void shouldUpdateCardSession() {

        final SessionResponse createSessionResponse = createHostedSession();

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

        final GetSessionResponse updated = blocking(() -> fourApi.sessionsClient().updateSession(created.getId(), appSession()));

        assertNotNull(updated);
        assertNotNull(updated.getId());

        assertNotNull(updated.getTransactionId());
        assertNotNull(updated.getAmount());
        assertNotNull(updated.getCard());
        assertEquals(AuthenticationType.REGULAR, updated.getAuthenticationType());
        assertEquals(Category.PAYMENT, updated.getAuthenticationCategory());
        assertEquals(SessionStatus.CHALLENGED, updated.getStatus());
        assertEquals(1, updated.getNextActions().size());
        assertEquals(NextAction.REDIRECT_CARDHOLDER, updated.getNextActions().get(0));
        assertNotNull(updated.getSelfLink());
        assertNotNull(updated.getLink("success_url"));
        assertNotNull(updated.getLink("failure_url"));
        assertNotNull(updated.getLink("redirect_url"));

    }

    @ParameterizedTest
    @MethodSource("authType")
    void shouldUpdate3dsMethodCompletionIndicator(final boolean usingSessionSecret) {

        final SessionResponse createSessionResponse = createHostedSession();

        assertNotNull(createSessionResponse);
        assertNotNull(createSessionResponse.getAccepted());

        final CreateSessionAcceptedResponse created = createSessionResponse.getAccepted();

        final ThreeDsMethodCompletionRequest threeDsMethodCompletionRequest = ThreeDsMethodCompletionRequest.builder()
                .threeDsMethodCompletion(ThreeDsMethodCompletion.Y)
                .build();

        final GetSessionResponseAfterChannelDataSupplied updated;

        if (usingSessionSecret) {
            updated = blocking(() -> fourApi.sessionsClient().update3dsMethodCompletionIndicator(created.getId(), threeDsMethodCompletionRequest));
        } else {
            updated = blocking(() -> fourApi.sessionsClient().update3dsMethodCompletionIndicator(created.getSessionSecret(), created.getId(), threeDsMethodCompletionRequest));
        }

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
