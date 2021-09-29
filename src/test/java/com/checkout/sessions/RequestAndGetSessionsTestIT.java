package com.checkout.sessions;

import com.checkout.sessions.channel.ChannelData;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class RequestAndGetSessionsTestIT extends AbstractSessionsTestIT {

    private static Stream<Arguments> sessionsTypes_browserSession() {
        return Stream.of(
                Arguments.of(Category.PAYMENT, ChallengeIndicator.NO_PREFERENCE, TransactionType.GOODS_SERVICE),
                Arguments.of(Category.NON_PAYMENT, ChallengeIndicator.CHALLENGE_REQUESTED, TransactionType.CHECK_ACCEPTANCE),
                Arguments.of(Category.NON_PAYMENT, ChallengeIndicator.CHALLENGE_REQUESTED_MANDATE, TransactionType.ACCOUNT_FUNDING)
        );
    }

    private static Stream<Arguments> sessionsTypes_appSession() {
        return Stream.of(
                Arguments.of(Category.PAYMENT, ChallengeIndicator.NO_PREFERENCE, TransactionType.GOODS_SERVICE),
                Arguments.of(Category.NON_PAYMENT, ChallengeIndicator.CHALLENGE_REQUESTED, TransactionType.CHECK_ACCEPTANCE),
                Arguments.of(Category.NON_PAYMENT, ChallengeIndicator.CHALLENGE_REQUESTED_MANDATE, TransactionType.ACCOUNT_FUNDING)
        );
    }

    @ParameterizedTest
    @MethodSource("sessionsTypes_browserSession")
    void shouldRequestAndGetCardSession_browserSession(final Category category,
                                                       final ChallengeIndicator challengeIndicator,
                                                       final TransactionType transactionType) {

        final ChannelData browserSession = browserSession();

        final SessionResponse sessionResponse = createNonHostedSession(browserSession, category, challengeIndicator, transactionType);

        assertNotNull(sessionResponse);
        assertNotNull(sessionResponse.getCreated());

        final CreateSessionOkResponse response = sessionResponse.getCreated();
        assertNotNull(response.getId());
        assertNotNull(response.getSessionSecret());
        assertNotNull(response.getTransactionId());
        assertNotNull(response.getAmount());
        assertNotNull(response.getCertificates());
        assertNotNull(response.getDs());
        assertNotNull(response.getAcs());
        assertNotNull(response.getCard());

        assertEquals(AuthenticationType.REGULAR, response.getAuthenticationType());
        assertEquals(category, response.getAuthenticationCategory());
        assertEquals(SessionStatus.CHALLENGED, response.getStatus());
        assertEquals(1, response.getNextActions().size());
        assertEquals(NextAction.CHALLENGE_CARDHOLDER, response.getNextActions().get(0));
        assertEquals(transactionType, response.getTransactionType());
        assertEquals(ResponseCode.C, response.getResponseCode());

        assertNotNull(response.getSelfLink());
        assertNotNull(response.getLink("callback_url"));
        assertFalse(response.getCompleted());

        final GetSessionResponse getSessionResponse = blocking(fourApi.sessionsClient().getSessionDetails(response.getId()));

        assertNotNull(getSessionResponse);

        assertNotNull(getSessionResponse.getId());
        assertNotNull(getSessionResponse.getSessionSecret());
        assertNotNull(getSessionResponse.getTransactionId());
        assertNotNull(getSessionResponse.getAmount());
        assertNotNull(getSessionResponse.getCertificates());
        assertNotNull(getSessionResponse.getDs());
        assertNotNull(getSessionResponse.getAcs());
        assertNotNull(getSessionResponse.getCard());

        assertEquals(AuthenticationType.REGULAR, getSessionResponse.getAuthenticationType());
        assertEquals(category, getSessionResponse.getAuthenticationCategory());
        assertEquals(SessionStatus.CHALLENGED, getSessionResponse.getStatus());
        assertEquals(1, getSessionResponse.getNextActions().size());
        assertEquals(NextAction.CHALLENGE_CARDHOLDER, getSessionResponse.getNextActions().get(0));
        assertEquals(transactionType, getSessionResponse.getTransactionType());
        assertEquals(ResponseCode.C, getSessionResponse.getResponseCode());

        assertNotNull(getSessionResponse.getSelfLink());
        assertNotNull(getSessionResponse.getLink("callback_url"));
        assertFalse(getSessionResponse.getCompleted());

        final GetSessionResponse getSessionSecretSessionResponse = blocking(fourApi.sessionsClient().getSessionDetails(response.getSessionSecret(), response.getId()));

        assertNull(getSessionSecretSessionResponse.getCertificates());
        assertNull(getSessionSecretSessionResponse.getSessionSecret());

        assertNotNull(getSessionSecretSessionResponse.getId());
        assertNotNull(getSessionSecretSessionResponse.getTransactionId());
        assertNotNull(getSessionSecretSessionResponse.getAmount());

        assertNotNull(getSessionSecretSessionResponse.getDs());
        assertNotNull(getSessionSecretSessionResponse.getAcs());
        assertNotNull(getSessionSecretSessionResponse.getCard());

        assertEquals(AuthenticationType.REGULAR, getSessionSecretSessionResponse.getAuthenticationType());
        assertEquals(category, getSessionSecretSessionResponse.getAuthenticationCategory());
        assertEquals(SessionStatus.CHALLENGED, getSessionSecretSessionResponse.getStatus());
        assertEquals(1, getSessionSecretSessionResponse.getNextActions().size());
        assertEquals(NextAction.CHALLENGE_CARDHOLDER, getSessionSecretSessionResponse.getNextActions().get(0));
        assertEquals(transactionType, getSessionSecretSessionResponse.getTransactionType());
        assertEquals(ResponseCode.C, getSessionSecretSessionResponse.getResponseCode());

        assertNotNull(getSessionSecretSessionResponse.getSelfLink());
        assertNotNull(getSessionSecretSessionResponse.getLink("callback_url"));
        assertFalse(getSessionSecretSessionResponse.getCompleted());

    }

    @ParameterizedTest
    @MethodSource("sessionsTypes_appSession")
    void shouldRequestAndGetCardSession_appSession(final Category category,
                                                   final ChallengeIndicator challengeIndicator,
                                                   final TransactionType transactionType) {

        final ChannelData appSession = appSession();

        final SessionResponse sessionResponse = createNonHostedSession(appSession, category, challengeIndicator, transactionType);

        assertNotNull(sessionResponse);
        assertNotNull(sessionResponse.getCreated());

        final CreateSessionOkResponse response = sessionResponse.getCreated();
        assertNotNull(response.getId());
        assertNotNull(response.getSessionSecret());
        assertNotNull(response.getTransactionId());
        assertNotNull(response.getAmount());
        assertNotNull(response.getCertificates());
        assertNotNull(response.getDs());
        assertNotNull(response.getCard());

        assertEquals(AuthenticationType.REGULAR, response.getAuthenticationType());
        assertEquals(category, response.getAuthenticationCategory());
        assertEquals(SessionStatus.CHALLENGED, response.getStatus());
        assertEquals(1, response.getNextActions().size());
        assertEquals(NextAction.AUTHENTICATE, response.getNextActions().get(0));
        assertEquals(transactionType, response.getTransactionType());

        assertNotNull(response.getSelfLink());
        assertNotNull(response.getLink("callback_url"));
        assertFalse(response.getCompleted());

        final GetSessionResponse getSessionResponse = blocking(fourApi.sessionsClient().getSessionDetails(response.getId()));

        assertNotNull(getSessionResponse);

        assertNotNull(getSessionResponse.getId());
        assertNotNull(getSessionResponse.getSessionSecret());
        assertNotNull(getSessionResponse.getTransactionId());
        assertNotNull(getSessionResponse.getAmount());
        assertNotNull(getSessionResponse.getCertificates());
        assertNotNull(getSessionResponse.getDs());
        assertNull(getSessionResponse.getAcs());
        assertNotNull(getSessionResponse.getCard());

        assertEquals(AuthenticationType.REGULAR, getSessionResponse.getAuthenticationType());
        assertEquals(category, getSessionResponse.getAuthenticationCategory());
        assertEquals(SessionStatus.CHALLENGED, getSessionResponse.getStatus());
        assertEquals(1, getSessionResponse.getNextActions().size());
        assertEquals(NextAction.AUTHENTICATE, getSessionResponse.getNextActions().get(0));
        assertEquals(transactionType, getSessionResponse.getTransactionType());
        assertNull(getSessionResponse.getResponseCode());

        assertNotNull(getSessionResponse.getSelfLink());
        assertNotNull(getSessionResponse.getLink("callback_url"));
        assertFalse(getSessionResponse.getCompleted());

        final GetSessionResponse getSessionSecretSessionResponse = blocking(fourApi.sessionsClient().getSessionDetails(response.getSessionSecret(), response.getId()));

        assertNull(getSessionSecretSessionResponse.getCertificates());
        assertNull(getSessionSecretSessionResponse.getSessionSecret());

        assertNotNull(getSessionSecretSessionResponse.getId());
        assertNotNull(getSessionSecretSessionResponse.getTransactionId());
        assertNotNull(getSessionSecretSessionResponse.getAmount());

        assertNotNull(getSessionSecretSessionResponse.getDs());
        assertNull(getSessionSecretSessionResponse.getAcs());
        assertNotNull(getSessionSecretSessionResponse.getCard());

        assertEquals(AuthenticationType.REGULAR, getSessionSecretSessionResponse.getAuthenticationType());
        assertEquals(category, getSessionSecretSessionResponse.getAuthenticationCategory());
        assertEquals(SessionStatus.CHALLENGED, getSessionSecretSessionResponse.getStatus());
        assertEquals(1, getSessionSecretSessionResponse.getNextActions().size());
        assertEquals(NextAction.AUTHENTICATE, getSessionSecretSessionResponse.getNextActions().get(0));
        assertEquals(transactionType, getSessionSecretSessionResponse.getTransactionType());
        assertNull(getSessionSecretSessionResponse.getResponseCode());

        assertNotNull(getSessionSecretSessionResponse.getSelfLink());
        assertNotNull(getSessionSecretSessionResponse.getLink("callback_url"));
        assertFalse(getSessionSecretSessionResponse.getCompleted());

    }

}
