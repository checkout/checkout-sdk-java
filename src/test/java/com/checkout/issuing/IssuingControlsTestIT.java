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
        assertNotNull(control);
        assertEquals(card.getId(), control.getTargetId());
        assertEquals(ControlType.VELOCITY_LIMIT, control.getControlType());
    }

    @Test
    void shouldGetCardControls() {
        final CardControlsQuery query = CardControlsQuery.builder()
                .targetId(card.getId())
                .build();

        final CardControlsQueryResponse response = blocking(() ->
                issuingApi.issuingClient().getCardControls(query),
                new HasControls(),
                5L);

        assertNotNull(response);
        assertFalse(response.getControls().isEmpty());
        for (CardControlResponse control : response.getControls()) {
            assertEquals(card.getId(), control.getTargetId());
        }
    }

    @Test
    void shouldGetCardControlDetails() {
        final CardControlResponse response = blocking(() ->
                issuingApi.issuingClient().getCardControlDetails(control.getId()));

        assertNotNull(response);
        assertEquals(control.getId(), response.getId());
        assertEquals(card.getId(), response.getTargetId());
        assertEquals(ControlType.VELOCITY_LIMIT, response.getControlType());
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
        final IdResponse response = blocking(() ->
                issuingApi.issuingClient().removeCardControl(control.getId()));

        assertNotNull(response);
        assertEquals(control.getId(), response.getId());
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

