package com.checkout.sessions;

import com.checkout.CheckoutApiException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class CompleteSessionsTestIT extends AbstractSessionsTestIT {

    private static Stream<Arguments> authType() {
        return Stream.of(
                Arguments.of(false),
                Arguments.of(true)
        );
    }

    @ParameterizedTest
    @MethodSource("authType")
    void shouldTryToCompleteCardSession_browserSession(final boolean usingSessionSecret) {

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

        tryComplete(usingSessionSecret, created.getId(), created.getSessionSecret());

    }

    private void tryComplete(final boolean usingSessionSecret, final String sessionId, final String sessionSecret) {
        if (!usingSessionSecret) {
            assertOperationNotAllowed(checkoutApi.sessionsClient().completeSession(sessionId));
        } else {
            assertOperationNotAllowed(checkoutApi.sessionsClient().completeSession(sessionSecret, sessionId));
        }
    }

    private void assertOperationNotAllowed(final CompletableFuture<?> future) {
        try {
            future.get();
            fail();
        } catch (final InterruptedException | ExecutionException e) {
            assertTrue(e.getCause() instanceof CheckoutApiException);
            final CheckoutApiException checkoutException = (CheckoutApiException) e.getCause();
            assertEquals("operation_not_allowed", checkoutException.getErrorDetails().get("error_type"));
            assertEquals(403, checkoutException.getHttpStatusCode());
        }
    }

}
