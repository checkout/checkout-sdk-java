package com.checkout.issuing;

import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cards.CardStatus;
import com.checkout.issuing.cards.requests.credentials.CardCredentialsQuery;
import com.checkout.issuing.cards.requests.enrollment.PasswordThreeDSEnrollmentRequest;
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
import org.apache.http.HttpStatus;
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
        validateCardCreation(card, cardholder.getId());
    }

    @Test
    void shouldGetCardDetails() {
        final CardDetailsResponse cardDetails = blocking(() ->
                issuingApi.issuingClient().getCardDetails(card.getId()));

        validateCardDetails(cardDetails, card.getId());
    }

    @Test
    void shouldEnrollThreeDSForCard() {
        final PasswordThreeDSEnrollmentRequest request = createThreeDSEnrollmentRequest();

        final ThreeDSEnrollmentResponse enrollmentResponse = blocking(() ->
                issuingApi.issuingClient().enrollThreeDS(card.getId(), request));

        validateThreeDSEnrollmentResponse(enrollmentResponse);
    }

    @Test
    void shouldUpdateThreeDSEnrollment() {
        final ThreeDSUpdateRequest updateRequest = createThreeDSUpdateRequest();

        final ThreeDSUpdateResponse updateResponse = blocking(() ->
                issuingApi.issuingClient().updateThreeDS(card.getId(), updateRequest));

        validateThreeDSUpdateResponse(updateResponse);
    }

    @Test
    void shouldGetThreeDSDetails() {
        final ThreeDSEnrollmentDetailsResponse enrollmentDetails = blocking(() ->
                issuingApi.issuingClient().getCardThreeDSDetails(card.getId()));

        validateThreeDSEnrollmentDetails(enrollmentDetails);
    }

    @Test
    void shouldActivateCard() {
        final VoidResponse activationResponse = blocking(() ->
                issuingApi.issuingClient().activateCard(card.getId()));

        validateActivateCardResponse(activationResponse);

        final CardDetailsResponse cardDetails = blocking(() ->
                issuingApi.issuingClient().getCardDetails(card.getId()));

        assertEquals(CardStatus.ACTIVE, cardDetails.getStatus());
    }

    @Test
    void shouldGetCardCredentials() {
        final CardCredentialsQuery query = createCardCredentialsQuery();

        final CardCredentialsResponse credentialsResponse = blocking(() ->
                issuingApi.issuingClient().getCardCredentials(card.getId(), query));

        validateCredentialsResponse(credentialsResponse);
    }

    @Test
    void shouldRevokeCard() {
        final CardResponse cardResponse = createCard(cardholder.getId(), true);

        final RevokeCardRequest request = createRevokeCardRequest();

        final VoidResponse revokeResponse = blocking(() ->
                issuingApi.issuingClient().revokeCard(cardResponse.getId(), request));

        assertNotNull(revokeResponse);
        assertEquals(HttpStatus.SC_OK, revokeResponse.getHttpStatusCode());

        final CardDetailsResponse cardDetails = blocking(() ->
                issuingApi.issuingClient().getCardDetails(cardResponse.getId()));

        assertEquals(CardStatus.REVOKED, cardDetails.getStatus());
    }

    @Test
    void shouldSuspendCard() {
        final CardResponse cardResponse = createCard(cardholder.getId(), true);

        final SuspendCardRequest request = createSuspendCardRequest();

        final VoidResponse suspendedResponse = blocking(() ->
                issuingApi.issuingClient().suspendCard(cardResponse.getId(), request));

        assertNotNull(suspendedResponse);
        assertEquals(HttpStatus.SC_OK, suspendedResponse.getHttpStatusCode());

        final CardDetailsResponse cardDetails = blocking(() ->
                issuingApi.issuingClient().getCardDetails(cardResponse.getId()));

        assertEquals(CardStatus.SUSPENDED, cardDetails.getStatus());
    }

    // Synchronous methods
    @Test
    void shouldCreateCardSync() {
        validateCardCreation(card, cardholder.getId());
    }

    @Test
    void shouldGetCardDetailsSync() {
        final CardDetailsResponse cardDetails = 
                issuingApi.issuingClient().getCardDetailsSync(card.getId());

        validateCardDetails(cardDetails, card.getId());
    }

    @Test
    void shouldEnrollThreeDSForCardSync() {
        final PasswordThreeDSEnrollmentRequest request = createThreeDSEnrollmentRequest();

        final ThreeDSEnrollmentResponse enrollmentResponse = 
                issuingApi.issuingClient().enrollThreeDSSync(card.getId(), request);

        validateThreeDSEnrollmentResponse(enrollmentResponse);
    }

    @Test
    void shouldUpdateThreeDSEnrollmentSync() {
        final ThreeDSUpdateRequest updateRequest = createThreeDSUpdateRequest();

        final ThreeDSUpdateResponse updateResponse = 
                issuingApi.issuingClient().updateThreeDSSync(card.getId(), updateRequest);

        validateThreeDSUpdateResponse(updateResponse);
    }

    @Test
    void shouldGetThreeDSDetailsSync() {
        final ThreeDSEnrollmentDetailsResponse enrollmentDetails = 
                issuingApi.issuingClient().getCardThreeDSDetailsSync(card.getId());

        validateThreeDSEnrollmentDetails(enrollmentDetails);
    }

    @Test
    void shouldActivateCardSync() {
        final VoidResponse activationResponse = 
                issuingApi.issuingClient().activateCardSync(card.getId());

        validateActivateCardResponse(activationResponse);

        final CardDetailsResponse cardDetails = 
                issuingApi.issuingClient().getCardDetailsSync(card.getId());

        assertEquals(CardStatus.ACTIVE, cardDetails.getStatus());
    }

    @Test
    void shouldGetCardCredentialsSync() {
        final CardCredentialsQuery query = createCardCredentialsQuery();

        final CardCredentialsResponse credentialsResponse = 
                issuingApi.issuingClient().getCardCredentialsSync(card.getId(), query);

        validateCredentialsResponse(credentialsResponse);
    }

    @Test
    void shouldRevokeCardSync() {
        final CardResponse cardResponse = createCard(cardholder.getId(), true);

        final RevokeCardRequest request = createRevokeCardRequest();

        final VoidResponse revokeResponse = 
                issuingApi.issuingClient().revokeCardSync(cardResponse.getId(), request);

        assertNotNull(revokeResponse);
        assertEquals(HttpStatus.SC_OK, revokeResponse.getHttpStatusCode());

        final CardDetailsResponse cardDetails = 
                issuingApi.issuingClient().getCardDetailsSync(cardResponse.getId());

        assertEquals(CardStatus.REVOKED, cardDetails.getStatus());
    }

    @Test
    void shouldSuspendCardSync() {
        final CardResponse cardResponse = createCard(cardholder.getId(), true);

        final SuspendCardRequest request = createSuspendCardRequest();

        final VoidResponse suspendedResponse = 
                issuingApi.issuingClient().suspendCardSync(cardResponse.getId(), request);

        assertNotNull(suspendedResponse);
        assertEquals(HttpStatus.SC_OK, suspendedResponse.getHttpStatusCode());

        final CardDetailsResponse cardDetails = 
                issuingApi.issuingClient().getCardDetailsSync(cardResponse.getId());

        assertEquals(CardStatus.SUSPENDED, cardDetails.getStatus());
    }

    // Common methods
    private PasswordThreeDSEnrollmentRequest createThreeDSEnrollmentRequest() {
        return PasswordThreeDSEnrollmentRequest.builder()
                .password("password123")
                .locale("en")
                .build();
    }

    private ThreeDSUpdateRequest createThreeDSUpdateRequest() {
        return ThreeDSUpdateRequest.builder()
                .password("newPassword123")
                .build();
    }

    private CardCredentialsQuery createCardCredentialsQuery() {
        return CardCredentialsQuery.builder()
                .credentials("number, cvc2")
                .build();
    }

    private RevokeCardRequest createRevokeCardRequest() {
        return RevokeCardRequest.builder()
                .reason(RevokeReason.REPORTED_LOST)
                .build();
    }

    private SuspendCardRequest createSuspendCardRequest() {
        return SuspendCardRequest.builder()
                .reason(SuspendReason.SUSPECTED_LOST)
                .build();
    }

    private void validateCardCreation(CardResponse cardResponse, String expectedCardholderId) {
        assertNotNull(cardResponse);
        assertNotNull(cardResponse.getId());
    }

    private void validateCardDetails(CardDetailsResponse cardDetails, String expectedCardId) {
        assertNotNull(cardDetails);
        assertEquals(expectedCardId, cardDetails.getId());
    }

    private void validateThreeDSEnrollmentResponse(ThreeDSEnrollmentResponse response) {
        assertNotNull(response);
    }

    private void validateThreeDSUpdateResponse(ThreeDSUpdateResponse response) {
        assertNotNull(response);
    }

    private void validateThreeDSEnrollmentDetails(ThreeDSEnrollmentDetailsResponse response) {
        assertNotNull(response);
    }

    private void validateActivateCardResponse(VoidResponse activatedResponse) {
        assertNotNull(activatedResponse);
        assertEquals(HttpStatus.SC_OK, activatedResponse.getHttpStatusCode());
    }

    private void validateCredentialsResponse(CardCredentialsResponse credentialsResponse) {
        assertNotNull(credentialsResponse);
        assertNotNull(credentialsResponse.getNumber());
        assertNotNull(credentialsResponse.getCvc2());
    }
}
