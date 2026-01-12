package com.checkout.issuing;

import com.checkout.common.IdResponse;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cards.responses.CardResponse;
import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.VelocityLimit;
import com.checkout.issuing.controls.requests.VelocityWindow;
import com.checkout.issuing.controls.requests.VelocityWindowType;
import com.checkout.issuing.controls.requests.create.CardControlRequest;
import com.checkout.issuing.controls.requests.create.VelocityCardControlRequest;
import com.checkout.issuing.controls.requests.query.CardControlsQuery;
import com.checkout.issuing.controls.requests.update.UpdateCardControlRequest;
import com.checkout.issuing.controls.responses.create.CardControlResponse;
import com.checkout.issuing.controls.responses.query.CardControlsQueryResponse;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("3ds not configured and should not create cards every time")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IssuingControlsTestIT extends BaseIssuingTestIT {

    private CardResponse card;

    private CardControlResponse control;

    @BeforeAll
    void setUp() {
        final CardholderResponse cardholder = createCardholder();
        card = createCard(cardholder.getId(), true);
        control = createCardControl(card.getId());
    }

    @Test
    void shouldCreateControl() {
        validateControlCreation(control, card.getId());
    }

    @Test
    void shouldGetCardControls() {
        final CardControlsQuery query = createCardControlsQuery();

        final CardControlsQueryResponse response = blocking(() ->
                issuingApi.issuingClient().getCardControls(query),
                new HasControls(),
                5L);

        validateCardControlsResponse(response, card.getId());
    }

    @Test
    void shouldGetCardControlDetails() {
        final CardControlResponse response = blocking(() ->
                issuingApi.issuingClient().getCardControlDetails(control.getId()));

        validateCardControlDetailsResponse(response, control.getId(), card.getId());
    }

    @Test
    void shouldUpdateCardControl() {
        final UpdateCardControlRequest request = UpdateCardControlRequest.builder()
                .description("New max spend of 1000€ per month for restaurants")
                .velocityLimit(VelocityLimit.builder()
                        .amountLimit(1000)
                        .velocityWindow(VelocityWindow.builder()
                                .type(VelocityWindowType.MONTHLY)
                                .build())
                        .build())
                .build();

        final CardControlResponse response = blocking(() ->
                issuingApi.issuingClient().updateCardControl(control.getId(), request));

        assertNotNull(response);
        assertEquals(control.getId(), response.getId());
        assertEquals("New max spend of 1000€ per month for restaurants", request.getDescription());
        assertEquals(ControlType.VELOCITY_LIMIT, response.getControlType());
    }

    @Test
    void shouldRemoveCardControl() {
        final CardControlResponse tempControl = createCardControl(card.getId());
        
        final IdResponse response = blocking(() ->
                issuingApi.issuingClient().removeCardControl(tempControl.getId()));

        validateRemoveCardControlResponse(response, tempControl.getId());
    }

    // Synchronous methods
    @Test
    void shouldCreateControlSync() {
        final CardControlResponse control = createCardControl(card.getId());
        validateControlCreation(control, card.getId());
    }

    @Test
    void shouldGetCardControlsSync() {
        final CardControlsQuery query = createCardControlsQuery();

        final CardControlsQueryResponse response = 
                issuingApi.issuingClient().getCardControlsSync(query);

        validateCardControlsResponseSync(response);
    }

    @Test
    void shouldGetCardControlDetailsSync() {
        final CardControlResponse response = 
                issuingApi.issuingClient().getCardControlDetailsSync(control.getId());

        validateCardControlDetailsResponse(response, control.getId(), card.getId());
    }

    @Test
    void shouldUpdateCardControlSync() {
        final UpdateCardControlRequest request = createUpdateCardControlRequest();

        final CardControlResponse response = 
                issuingApi.issuingClient().updateCardControlSync(control.getId(), request);

        validateUpdatedCardControlResponse(response, control.getId());
    }

    @Test
    void shouldRemoveCardControlSync() {
        final CardControlResponse tempControl = createCardControl(card.getId());
        
        final IdResponse response = 
                issuingApi.issuingClient().removeCardControlSync(tempControl.getId());

        validateRemoveCardControlResponse(response, tempControl.getId());
    }

    // Common methods
    private CardControlsQuery createCardControlsQuery() {
        return CardControlsQuery.builder()
                .targetId(card.getId())
                .build();
    }

    private UpdateCardControlRequest createUpdateCardControlRequest() {
        return UpdateCardControlRequest.builder()
                .description("New max spend of 1000€ per month for restaurants")
                .velocityLimit(VelocityLimit.builder()
                        .amountLimit(1000)
                        .velocityWindow(VelocityWindow.builder()
                                .type(VelocityWindowType.MONTHLY)
                                .build())
                        .build())
                .build();
    }

    private CardControlResponse createCardControl(final String cardId) {
        final CardControlRequest request = VelocityCardControlRequest.builder()
                .description("Max spend of 500€ per week for restaurants")
                .targetId(cardId)
                .velocityLimit(VelocityLimit.builder()
                        .amountLimit(500)
                        .velocityWindow(VelocityWindow.builder()
                                .type(VelocityWindowType.WEEKLY)
                                .build())
                        .build())
                .build();

        final CardControlResponse cardControlResponse = blocking(() ->
                issuingApi.issuingClient().createControl(request));

        assertNotNull(cardControlResponse);
        return cardControlResponse;
    }

    private void validateControlCreation(CardControlResponse control, String expectedTargetId) {
        assertNotNull(control);
        assertEquals(expectedTargetId, control.getTargetId());
        assertEquals(ControlType.VELOCITY_LIMIT, control.getControlType());
    }

    private void validateCardControlsResponse(CardControlsQueryResponse response, String expectedTargetId) {
        assertNotNull(response);
        assertFalse(response.getControls().isEmpty());
        for (CardControlResponse control : response.getControls()) {
            assertEquals(expectedTargetId, control.getTargetId());
        }
    }

    private void validateCardControlsResponseSync(CardControlsQueryResponse response) {
        assertNotNull(response);
        // Note: May be empty if controls were removed by async tests
        for (CardControlResponse control : response.getControls()) {
            assertEquals(card.getId(), control.getTargetId());
        }
    }

    private void validateCardControlDetailsResponse(CardControlResponse response, String expectedId, String expectedTargetId) {
        assertNotNull(response);
        assertEquals(expectedId, response.getId());
        assertEquals(expectedTargetId, response.getTargetId());
        assertEquals(ControlType.VELOCITY_LIMIT, response.getControlType());
    }

    private void validateUpdatedCardControlResponse(CardControlResponse response, String expectedId) {
        assertNotNull(response);
        assertEquals(expectedId, response.getId());
        assertEquals(ControlType.VELOCITY_LIMIT, response.getControlType());
    }

    private void validateRemoveCardControlResponse(IdResponse response, String expectedId) {
        assertNotNull(response);
        assertEquals(expectedId, response.getId());
    }

    protected static class HasControls extends BaseMatcher<CardControlsQueryResponse> {

        public HasControls() {}

        @Override
        public boolean matches(final Object actual) {
            if (!(actual instanceof CardControlsQueryResponse)) {
                throw new IllegalStateException("not a CardControlsQueryResponse!");
            }
            return ((CardControlsQueryResponse) actual).getControls().size() > 0;
        }

        @Override
        public void describeTo(final Description description) {
            throw new UnsupportedOperationException();
        }
    }
}

