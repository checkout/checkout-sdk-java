package com.checkout.issuing;

import com.checkout.TestHelper;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cards.CardStatus;
import com.checkout.issuing.cards.requests.credentials.CardCredentialsQuery;
import com.checkout.issuing.cards.requests.enrollment.PasswordThreeDSEnrollmentRequest;
import com.checkout.issuing.cards.requests.enrollment.SecurityPair;
import com.checkout.issuing.cards.requests.enrollment.ThreeDSUpdateRequest;
import com.checkout.issuing.cards.requests.revoke.RevokeCardRequest;
import com.checkout.issuing.cards.requests.revoke.RevokeReason;
import com.checkout.issuing.cards.requests.suspend.SuspendCardRequest;
import com.checkout.issuing.cards.requests.suspend.SuspendReason;
import com.checkout.issuing.cards.responses.CardDetailsResponse;
import com.checkout.issuing.cards.responses.CardResponse;
import com.checkout.issuing.cards.responses.credentials.CardCredentialsResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSEnrollmentDetailsResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSEnrollmentResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSUpdateResponse;
import com.checkout.payments.VoidResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("Avoid creating cards all the time")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IssuingCardsTestIT extends BaseIssuingTestIT {

    private CardholderResponse cardholder;

    private CardResponse card;

    @BeforeAll
    void setUp() {
        cardholder = createCardholder();
        card = createCard(cardholder.getId());
    }

    @Test
    void shouldCreateCard() {
        assertNotNull(card);
        assertEquals("JOHN KENNEDY", card.getDisplayName());
        assertEquals("X-123456-N11", card.getReference());
    }

    @Test
    void shouldGetCardDetails() {
        final CardDetailsResponse cardDetails = blocking(() ->
                issuingApi.issuingClient().getCardDetails(card.getId()));

        assertNotNull(cardDetails);
        assertEquals(card.getId(), cardDetails.getId());
        assertEquals(cardholder.getId(), cardDetails.getCardholderId());
        assertEquals("pro_3fn6pv2ikshurn36dbd3iysyha", cardDetails.getCardProductId());
        assertEquals("X-123456-N11", cardDetails.getReference());
    }

    @Test
    void shouldEnrollCardIntoThreeDS() {
        final PasswordThreeDSEnrollmentRequest request = PasswordThreeDSEnrollmentRequest.builder()
                .password("Xtui43FvfiZ")
                .locale("en-US")
                .phoneNumber(TestHelper.createPhone())
                .build();

        final ThreeDSEnrollmentResponse enrollmentResponse = blocking(() ->
                issuingApi.issuingClient().enrollThreeDS(card.getId(), request));

        assertNotNull(enrollmentResponse);
        assertEquals(HttpStatus.SC_ACCEPTED, enrollmentResponse.getHttpStatusCode());
    }

    @Test
    void shouldUpdateThreeDSEnrollment() {
        final ThreeDSUpdateRequest updateRequest = ThreeDSUpdateRequest.builder()
                .securityPair(SecurityPair.builder()
                        .question("Who are you?")
                        .answer("Bond. James Bond.")
                        .build())
                .password("Xtui43FvfiZ")
                .locale("en-US")
                .phoneNumber(TestHelper.createPhone())
                .build();

        final ThreeDSUpdateResponse updateResponse = blocking(() ->
                issuingApi.issuingClient().updateThreeDS(card.getId(), updateRequest));

        assertNotNull(updateResponse);
        assertEquals(HttpStatus.SC_ACCEPTED, updateResponse.getHttpStatusCode());
    }

    @Test
    void shouldGetThreeDSDetails() {
        final ThreeDSEnrollmentDetailsResponse enrollmentDetails = blocking(() ->
                issuingApi.issuingClient().getCardThreeDSDetails(card.getId()));

        assertNotNull(enrollmentDetails);
        assertEquals("en-US", enrollmentDetails.getLocale());
        assertEquals("1", enrollmentDetails.getPhoneNumber().getCountryCode());
        assertEquals("4155552671", enrollmentDetails.getPhoneNumber().getNumber());
    }

    @Test
    void shouldActivateCard() {
        final VoidResponse activationResponse = blocking(() ->
                issuingApi.issuingClient().activateCard(card.getId()));

        assertNotNull(activationResponse);
        assertEquals(HttpStatus.SC_OK, activationResponse.getHttpStatusCode());

        final CardDetailsResponse cardDetails = blocking(() ->
                issuingApi.issuingClient().getCardDetails(card.getId()));

        assertNotNull(cardDetails);
        assertEquals(CardStatus.ACTIVE, cardDetails.getStatus());
    }

    @Test
    void shouldGetCardCredentials() {
        final CardCredentialsQuery query = CardCredentialsQuery.builder()
                .credentials("number, cvc2")
                .build();

        final CardCredentialsResponse credentialsResponse = blocking(() ->
                issuingApi.issuingClient().getCardCredentials(card.getId(), query));

        assertNotNull(credentialsResponse);
        assertNotNull(credentialsResponse.getNumber());
        assertNotNull(credentialsResponse.getCvc2());
    }

    @Test
    void shouldRevokeCard() {
        final CardResponse cardResponse = createCard(cardholder.getId(), true);

        final RevokeCardRequest request = RevokeCardRequest.builder()
                .reason(RevokeReason.REPORTED_LOST)
                .build();

        final VoidResponse revokeResponse = blocking(() ->
                issuingApi.issuingClient().revokeCard(cardResponse.getId(), request));

        assertNotNull(revokeResponse);
        assertEquals(HttpStatus.SC_OK, revokeResponse.getHttpStatusCode());

        final CardDetailsResponse cardDetails = blocking(() ->
                issuingApi.issuingClient().getCardDetails(cardResponse.getId()));

        assertNotNull(cardDetails);
        assertEquals(CardStatus.REVOKED, cardDetails.getStatus());
    }

    @Test
    void shouldSuspendCard() {
        final CardResponse cardResponse = createCard(cardholder.getId(), true);

        final SuspendCardRequest request = SuspendCardRequest.builder()
                .reason(SuspendReason.SUSPECTED_LOST)
                .build();

        final VoidResponse suspendedResponse = blocking(() ->
                issuingApi.issuingClient().suspendCard(cardResponse.getId(), request));

        assertNotNull(suspendedResponse);
        assertEquals(HttpStatus.SC_OK, suspendedResponse.getHttpStatusCode());

        final CardDetailsResponse cardDetails = blocking(() ->
                issuingApi.issuingClient().getCardDetails(cardResponse.getId()));

        assertNotNull(cardDetails);
        assertEquals(CardStatus.SUSPENDED, cardDetails.getStatus());
    }
}
