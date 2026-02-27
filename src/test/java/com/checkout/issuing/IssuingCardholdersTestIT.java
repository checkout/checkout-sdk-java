package com.checkout.issuing;

import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.cardholders.CardholderDetailsResponse;
import com.checkout.issuing.cardholders.CardholderAccessTokenRequest;
import com.checkout.issuing.cardholders.CardholderAccessTokenResponse;
import com.checkout.issuing.cardholders.CardholderUpdateRequest;
import com.checkout.issuing.cardholders.CardholderUpdateResponse;
import com.checkout.issuing.cardholders.CardholderRequest;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cardholders.CardholderStatus;
import com.checkout.issuing.cardholders.CardholderType;
import com.checkout.issuing.cards.responses.CardDetailsResponse;
import com.checkout.TestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IssuingCardholdersTestIT extends BaseIssuingTestIT {

    private CardholderResponse cardholder;

    @BeforeAll
    void setUp() {
        cardholder = createCardholder();
    }

    @Test
    void shouldCreateCardholder() {
        final CardholderResponse cardholderResponse = cardholder;

        validateCardholderCreation(cardholderResponse);
    }

    @Test
    void shouldGetCardholderDetails() {
        final CardholderDetailsResponse cardholderDetails = blocking(() ->
                issuingApi.issuingClient().getCardholder(cardholder.getId()));

        validateCardholderDetails(cardholderDetails);
    }

    @Test
    void shouldGetCardholderCards() {
        final CardholderCardsResponse cardholderCards = blocking(() ->
                issuingApi.issuingClient().getCardholderCards(cardholder.getId()));

        validateCardholderCards(cardholderCards);
    }

    @Test
    void shouldRequestCardholderAccessToken() {
        final CardholderAccessTokenRequest request = createCardholderAccessTokenRequest();

        final CardholderAccessTokenResponse response = blocking(() ->
                issuingApi.issuingClient().requestCardholderAccessToken(request));

        validateCardholderAccessTokenResponse(response);
    }

    @Test
    void shouldUpdateCardholder() {
        final CardholderUpdateRequest request = createCardholderUpdateRequest();

        final CardholderUpdateResponse response = blocking(() ->
                issuingApi.issuingClient().updateCardholder(cardholder.getId(), request));

        validateCardholderUpdateResponse(response);
    }

    @Test
    void shouldCreateUpdateAndVerifyCardholder() {
        // Create a new cardholder
        final CardholderRequest createRequest = createCardholderRequest();
        final CardholderResponse createResponse = blocking(() ->
                issuingApi.issuingClient().createCardholder(createRequest));

        validateCardholderCreation(createResponse, createRequest);

        // Update the cardholder
        final CardholderUpdateRequest updateRequest = createCardholderUpdateRequestForVerification();
        final CardholderUpdateResponse updateResponse = blocking(() ->
                issuingApi.issuingClient().updateCardholder(createResponse.getId(), updateRequest));

        validateCardholderUpdateResponse(updateResponse);

        // Get updated cardholder and verify changes
        final CardholderDetailsResponse getResponse = blocking(() ->
                issuingApi.issuingClient().getCardholder(createResponse.getId()));

        validateUpdatedCardholderDetails(getResponse, updateRequest);
    }

    // Synchronous methods
    @Test
    void shouldCreateCardholderSync() {
        final CardholderResponse cardholderResponse = createCardholder();

        validateCardholderCreation(cardholderResponse);
    }

    @Test
    void shouldGetCardholderDetailsSync() {
        final CardholderDetailsResponse cardholderDetails = 
                issuingApi.issuingClient().getCardholderSync(cardholder.getId());

        validateCardholderDetails(cardholderDetails);
    }

    @Test
    void shouldGetCardholderCardsSync() {
        final CardholderCardsResponse cardholderCards = 
                issuingApi.issuingClient().getCardholderCardsSync(cardholder.getId());

        validateCardholderCards(cardholderCards);
    }

    @Test
    void shouldRequestCardholderAccessTokenSync() {
        final CardholderAccessTokenRequest request = createCardholderAccessTokenRequest();

        final CardholderAccessTokenResponse response = 
                issuingApi.issuingClient().requestCardholderAccessTokenSync(request);

        validateCardholderAccessTokenResponse(response);
    }

    @Test
    void shouldUpdateCardholderSync() {
        final CardholderUpdateRequest request = createCardholderUpdateRequest();

        final CardholderUpdateResponse response = 
                issuingApi.issuingClient().updateCardholderSync(cardholder.getId(), request);

        validateCardholderUpdateResponse(response);
    }

    @Test
    void shouldCreateUpdateAndVerifyCardholderSync() {
        // Create a new cardholder
        final CardholderRequest createRequest = createCardholderRequest();
        final CardholderResponse createResponse = 
                issuingApi.issuingClient().createCardholderSync(createRequest);

        validateCardholderCreation(createResponse, createRequest);

        // Update the cardholder
        final CardholderUpdateRequest updateRequest = createCardholderUpdateRequestForVerification();
        final CardholderUpdateResponse updateResponse = 
                issuingApi.issuingClient().updateCardholderSync(createResponse.getId(), updateRequest);

        validateCardholderUpdateResponse(updateResponse);

        // Get updated cardholder and verify changes
        final CardholderDetailsResponse getResponse = 
                issuingApi.issuingClient().getCardholderSync(createResponse.getId());

        validateUpdatedCardholderDetails(getResponse, updateRequest);
    }

    // Common methods
    private CardholderRequest createCardholderRequest() {
        return CardholderRequest.builder()
                .type(CardholderType.INDIVIDUAL)
                .reference("X-TEST-" + System.currentTimeMillis())
                .entityId("ent_mujh2nia2ypezmw5fo2fofk7ka")
                .firstName("John")
                .middleName("Fitzgerald")
                .lastName("Kennedy")
                .email("john.kennedy@myemaildomain.com")
                .phoneNumber(TestHelper.createPhone())
                .dateOfBirth("1985-05-15")
                .billingAddress(TestHelper.createAddress())
                .residencyAddress(TestHelper.createAddress())
                .build();
    }

    private CardholderUpdateRequest createCardholderUpdateRequest() {
        return CardholderUpdateRequest.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@updatedemails.com")
                .build();
    }

    private CardholderUpdateRequest createCardholderUpdateRequestForVerification() {
        return CardholderUpdateRequest.builder()
                .firstName("UpdatedJohn")
                .middleName("UpdatedMiddle")
                .lastName("UpdatedSurname")
                .email("updated.john@newdomain.com")
                .phoneNumber(TestHelper.createPhone())
                .dateOfBirth("1990-12-25")
                .billingAddress(TestHelper.createAddress())
                .residencyAddress(TestHelper.createAddress())
                .build();
    }
    private CardholderAccessTokenRequest createCardholderAccessTokenRequest() {
        return CardholderAccessTokenRequest.builder()
                .grantType("client_credentials")
                .clientID(System.getenv("CHECKOUT_DEFAULT_OAUTH_ISSUING_CLIENT_ID"))
                .clientSecret(System.getenv("CHECKOUT_DEFAULT_OAUTH_ISSUING_CLIENT_SECRET"))
                .cardholderId(cardholder.getId())
                .singleUse(true)
                .build();
    }

    private void validateCardholderAccessTokenResponse(CardholderAccessTokenResponse response) {
        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getTokenType());
        assertNotNull(response.getExpiresIn());
        // Note: Scope might be null in some cases according to OAuth spec
    }

    private void validateCardholderUpdateResponse(CardholderUpdateResponse response) {
        assertNotNull(response);
        assertNotNull(response.getLastModifiedDate());
    }

    private void validateUpdatedCardholderDetails(CardholderDetailsResponse response, CardholderUpdateRequest updateRequest) {
        assertNotNull(response);
        assertEquals(updateRequest.getFirstName(), response.getFirstName());
        assertEquals(updateRequest.getMiddleName(), response.getMiddleName());
        assertEquals(updateRequest.getLastName(), response.getLastName());
        assertEquals(updateRequest.getEmail(), response.getEmail());
        assertEquals(updateRequest.getDateOfBirth(), response.getDateOfBirth());
        assertNotNull(response.getLastModifiedDate());
        
        if (updateRequest.getPhoneNumber() != null) {
            assertNotNull(response.getPhoneNumber());
        }
        if (updateRequest.getBillingAddress() != null) {
            assertNotNull(response.getBillingAddress());
        }
        if (updateRequest.getResidencyAddress() != null) {
            assertNotNull(response.getResidencyAddress());
        }
    }

    private void validateCardholderCreation(CardholderResponse cardholderResponse) {
        assertNotNull(cardholderResponse);
        assertEquals(CardholderType.INDIVIDUAL, cardholderResponse.getType());
        assertEquals(CardholderStatus.ACTIVE, cardholderResponse.getStatus());
        assertEquals("X-123456-N11", cardholderResponse.getReference());
    }

    private void validateCardholderCreation(CardholderResponse cardholderResponse, CardholderRequest originalRequest) {
        assertNotNull(cardholderResponse);
        assertEquals(CardholderType.INDIVIDUAL, cardholderResponse.getType());
        assertEquals(CardholderStatus.ACTIVE, cardholderResponse.getStatus());
        assertEquals(originalRequest.getReference(), cardholderResponse.getReference());
    }

    private void validateCardholderDetails(CardholderDetailsResponse cardholderDetails) {
        assertNotNull(cardholderDetails);
        assertEquals(CardholderType.INDIVIDUAL, cardholderDetails.getType());
        assertEquals("X-123456-N11", cardholderDetails.getReference());
    }

    private void validateCardholderCards(CardholderCardsResponse cardholderCards) {
        assertNotNull(cardholderCards);
        for (final CardDetailsResponse card : cardholderCards.getCards()) {
            assertEquals(cardholder.getId(), card.getId());
            assertEquals("cli_p6jeowdtuxku3azxgt2qa7kq7a", card.getClientId());
        }
    }
}
