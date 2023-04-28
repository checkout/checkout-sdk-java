package com.checkout.issuing;

import com.checkout.CheckoutApi;
import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import com.checkout.OAuthScope;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.DocumentType;
import com.checkout.common.IdResponse;
import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.cardholders.CardholderDetailsResponse;
import com.checkout.issuing.cardholders.CardholderDocument;
import com.checkout.issuing.cardholders.CardholderRequest;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cardholders.CardholderStatus;
import com.checkout.issuing.cardholders.CardholderType;
import com.checkout.issuing.cards.CardStatus;
import com.checkout.issuing.cards.requests.create.CardLifetime;
import com.checkout.issuing.cards.requests.create.LifetimeUnit;
import com.checkout.issuing.cards.requests.create.VirtualCardRequest;
import com.checkout.issuing.cards.requests.credentials.CardCredentialsQuery;
import com.checkout.issuing.cards.requests.enrollment.PasswordThreeDSEnrollmentRequest;
import com.checkout.issuing.cards.requests.enrollment.SecurityPair;
import com.checkout.issuing.cards.requests.enrollment.ThreeDSUpdateRequest;
import com.checkout.issuing.cards.requests.revoke.RevokeCardRequest;
import com.checkout.issuing.cards.requests.revoke.RevokeReason;
import com.checkout.issuing.cards.requests.suspend.SuspendCardRequest;
import com.checkout.issuing.cards.requests.suspend.SuspendReason;
import com.checkout.issuing.cards.responses.AbstractCardDetailsResponse;
import com.checkout.issuing.cards.responses.CardResponse;
import com.checkout.issuing.cards.responses.credentials.CardCredentialsResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSEnrollmentDetailsResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSEnrollmentResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSUpdateResponse;
import com.checkout.issuing.controls.requests.ControlType;
import com.checkout.issuing.controls.requests.VelocityLimit;
import com.checkout.issuing.controls.requests.VelocityWindow;
import com.checkout.issuing.controls.requests.VelocityWindowType;
import com.checkout.issuing.controls.requests.create.AbstractCardControlRequest;
import com.checkout.issuing.controls.requests.create.VelocityCardControlRequest;
import com.checkout.issuing.controls.requests.query.CardControlsQuery;
import com.checkout.issuing.controls.requests.update.UpdateCardControlRequest;
import com.checkout.issuing.controls.responses.create.AbstractCardControlResponse;
import com.checkout.issuing.controls.responses.query.CardControlsQueryResponse;
import com.checkout.payments.VoidResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IssuingTestIT extends SandboxTestFixture {

    private CardholderResponse cardholder;

    private CardResponse card;

    private AbstractCardControlResponse control;

    public IssuingTestIT() { super(PlatformType.DEFAULT_OAUTH); }

    @BeforeAll
    void setUp() {
        cardholder = createCardholder();
        card = createCard(cardholder.getId());
        control = createCardControl(card.getId());
    }

    @Nested
    @DisplayName("Cardholders")
    class Cardholders {
        @Test
        void shouldCreateCardholder() {
            final CardholderRequest cardholderRequest = CardholderRequest.builder()
                    .type(CardholderType.INDIVIDUAL)
                    .reference("X-123456-N11")
                    .entityId("ent_mujh2nia2ypezmw5fo2fofk7ka")
                    .firstName("John")
                    .middleName("Fitzgerald")
                    .lastName("Kennedy")
                    .email("john.kennedy@myemaildomain.com")
                    .phoneNumber(TestHelper.createPhone())
                    .dateOfBirth("1985-05-15")
                    .billingAddress(TestHelper.createAddress())
                    .residencyAddress(TestHelper.createAddress())
                    .document(
                            CardholderDocument.builder()
                                    .type(DocumentType.NATIONAL_IDENTITY_CARD)
                                    .frontDocumentId("file_6lbss42ezvoufcb2beo76rvwly")
                                    .backDocumentId("file_aaz5pemp6326zbuvevp6qroqu4")
                                    .build())
                    .build();

            final CardholderResponse cardholderResponse = blocking(() ->
                getIssuingCheckoutApi()
                    .issuingClient()
                    .createCardholder(cardholderRequest));

            assertNotNull(cardholderResponse);
            assertEquals(CardholderType.INDIVIDUAL, cardholderResponse.getType());
            assertEquals(CardholderStatus.ACTIVE, cardholderResponse.getStatus());
            assertEquals("X-123456-N11", cardholderResponse.getReference());
        }

        @Test
        void shouldGetCardholderDetails() {
            final CardholderDetailsResponse cardholderDetails = blocking(() ->
                    getIssuingCheckoutApi()
                        .issuingClient()
                        .getCardholder(cardholder.getId()));

            assertNotNull(cardholderDetails);
            assertEquals(CardholderType.INDIVIDUAL, cardholderDetails.getType());
            assertEquals("X-123456-N11", cardholderDetails.getReference());
        }

        @Test
        void shouldGetCardholderCards() {
            final CardholderCardsResponse cardholderCards = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .getCardholderCards(cardholder.getId()));

            assertNotNull(cardholderCards);
            for (AbstractCardDetailsResponse card : cardholderCards.getCards()) {
                assertEquals(cardholder.getId(), card.getCardholderId());
                assertEquals("cli_p6jeowdtuxku3azxgt2qa7kq7a", card.getClientId());
            }
        }
    }

    @Nested
    @DisplayName("Cards")
    class Cards {
        @Test
        void shouldCreateCard() {
            final VirtualCardRequest request = VirtualCardRequest.builder()
                    .cardholderId(cardholder.getId())
                    .cardProductId("pro_3fn6pv2ikshurn36dbd3iysyha")
                    .lifetime(CardLifetime.builder()
                            .unit(LifetimeUnit.MONTHS)
                            .value(6)
                            .build())
                    .reference("X-123456-N11")
                    .displayName("John Kennedy")
                    .isSingleUse(false)
                    .activateCard(false)
                    .build();

            final CardResponse cardResponse = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .createCard(request));

            assertNotNull(cardResponse);
            assertEquals("X-123456-N11", cardResponse.getReference());
        }

        @Test
        void shouldGetCardDetails() {
            final AbstractCardDetailsResponse cardDetails = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .getCardDetails(card.getId()));

            assertNotNull(cardDetails);
            assertEquals(card.getId(), cardDetails.getId());
            assertEquals(cardholder.getId(), cardDetails.getCardholderId());
            assertEquals("pro_3fn6pv2ikshurn36dbd3iysyha", cardDetails.getCardProductId());
            assertEquals("X-123456-N11", cardDetails.getReference());
        }

        @Test
        @Disabled("client not configured for 3DS")
        void shouldEnrollCardIntoThreeDS() {
            final PasswordThreeDSEnrollmentRequest request = PasswordThreeDSEnrollmentRequest.builder()
                    .password("Xtui43FvfiZ")
                    .locale("en-US")
                    .phoneNumber(TestHelper.createPhone())
                    .build();

            final ThreeDSEnrollmentResponse enrollmentResponse = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .enrollThreeDS(card.getId(), request));

            assertNotNull(enrollmentResponse);
            assertEquals(HttpStatus.SC_ACCEPTED, enrollmentResponse.getHttpStatusCode());
        }

        @Test
        @Disabled("client not configured for 3DS")
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
                getIssuingCheckoutApi()
                        .issuingClient()
                        .updateThreeDS(card.getId(), updateRequest));

            assertNotNull(updateResponse);
            assertEquals(HttpStatus.SC_ACCEPTED, updateResponse.getHttpStatusCode());
        }

        @Test
        @Disabled("client not configured for 3DS")
        void shouldGetThreeDSDetails() {
            final ThreeDSEnrollmentDetailsResponse enrollmentDetails = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .getCardThreeDSDetails(card.getId()));

            assertNotNull(enrollmentDetails);
            assertEquals("en-US", enrollmentDetails.getLocale());
            assertEquals("1", enrollmentDetails.getPhoneNumber().getCountryCode());
            assertEquals("4155552671", enrollmentDetails.getPhoneNumber().getNumber());
        }

        @Test
        void shouldActivateCard() {
            final VoidResponse activationResponse = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .activateCard(card.getId()));

            assertNotNull(activationResponse);
            assertEquals(HttpStatus.SC_OK, activationResponse.getHttpStatusCode());

            final AbstractCardDetailsResponse cardDetails = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .getCardDetails(card.getId()));

            assertNotNull(cardDetails);
            assertEquals(CardStatus.ACTIVE, cardDetails.getStatus());
        }

        @Test
        void shouldGetCardCredentials() {
            final CardCredentialsQuery query = CardCredentialsQuery.builder()
                    .credentials("number, cvc2")
                    .build();

            final CardCredentialsResponse credentialsResponse = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .getCardCredentials(card.getId(), query));

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
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .revokeCard(cardResponse.getId(), request));

            assertNotNull(revokeResponse);
            assertEquals(HttpStatus.SC_OK, revokeResponse.getHttpStatusCode());

            final AbstractCardDetailsResponse cardDetails = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .getCardDetails(cardResponse.getId()));

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
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .suspendCard(cardResponse.getId(), request));

            assertNotNull(suspendedResponse);
            assertEquals(HttpStatus.SC_OK, suspendedResponse.getHttpStatusCode());

            final AbstractCardDetailsResponse cardDetails = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .getCardDetails(cardResponse.getId()));

            assertNotNull(cardDetails);
            assertEquals(CardStatus.SUSPENDED, cardDetails.getStatus());
        }
    }

    @Nested
    @DisplayName("Controls")
    class Controls {
        @Test
        void shouldCreateControl() {
            final CardholderResponse cardholder = createCardholder();
            final CardResponse card = createCard(cardholder.getId());

            final AbstractCardControlRequest request = VelocityCardControlRequest.builder()
                    .description("Max spend of 500€ per week for restaurants")
                    .targetId(card.getId())
                    .velocityLimit(VelocityLimit.builder()
                            .amountLimit(500)
                            .velocityWindow(VelocityWindow.builder()
                                    .type(VelocityWindowType.WEEKLY)
                                    .build())
                            .build())
                    .build();

            final AbstractCardControlResponse cardControlResponse = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .createControl(request));

            assertNotNull(cardControlResponse);
            assertEquals(card.getId(), cardControlResponse.getTargetId());
            assertEquals(ControlType.VELOCITY_LIMIT, cardControlResponse.getControlType());
        }

        @Test
        void shouldGetCardControls() {
            final CardControlsQuery query = CardControlsQuery.builder()
                    .targetId(card.getId())
                    .build();

            final CardControlsQueryResponse response = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .getCardControls(query));

            assertNotNull(response);
            for (AbstractCardControlResponse control : response.getControls()) {
                assertEquals(card.getId(), control.getTargetId());
            }
        }

        @Test
        @Disabled("not-found")
        void shouldGetCardControlDetails() {
            final AbstractCardControlResponse response = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .getCardControlDetails(control.getId()));

            assertNotNull(response);
            assertEquals(control.getId(), response.getId());
            assertEquals(card.getId(), response.getTargetId());
            assertEquals(ControlType.VELOCITY_LIMIT, response.getControlType());
        }

        @Test
        @Disabled("not-found")
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

            final AbstractCardControlResponse response = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .updateCardControl(control.getId(), request));

            assertNotNull(response);
            assertEquals(control.getId(), response.getId());
            assertEquals("New max spend of 1000€ per month for restaurants", request.getDescription());
            assertEquals(ControlType.VELOCITY_LIMIT, response.getControlType());
        }

        @Test
        void shouldRemoveCardControl() {
            final IdResponse response = blocking(() ->
                    getIssuingCheckoutApi()
                            .issuingClient()
                            .removeCardControl(control.getId()));

            assertNotNull(response);
            assertEquals(control.getId(), response.getId());
        }
    }

    private CardholderResponse createCardholder() {
        final CardholderRequest cardholderRequest = CardholderRequest.builder()
                .type(CardholderType.INDIVIDUAL)
                .reference("X-123456-N11")
                .entityId("ent_mujh2nia2ypezmw5fo2fofk7ka")
                .firstName("John")
                .middleName("Fitzgerald")
                .lastName("Kennedy")
                .email("john.kennedy@myemaildomain.com")
                .phoneNumber(TestHelper.createPhone())
                .dateOfBirth("1985-05-15")
                .billingAddress(TestHelper.createAddress())
                .residencyAddress(TestHelper.createAddress())
                .document(
                        CardholderDocument.builder()
                                .type(DocumentType.NATIONAL_IDENTITY_CARD)
                                .frontDocumentId("file_6lbss42ezvoufcb2beo76rvwly")
                                .backDocumentId("file_aaz5pemp6326zbuvevp6qroqu4")
                                .build())
                .build();

        final CardholderResponse cardholderResponse = blocking(() ->
                getIssuingCheckoutApi()
                        .issuingClient()
                        .createCardholder(cardholderRequest));

        assertNotNull(cardholderResponse);

        return cardholderResponse;
    }

    private CardResponse createCard(String cardholderId) {
        return this.createCard(cardholderId, false);
    }

    private CardResponse createCard(String cardholderId, Boolean active) {
        final VirtualCardRequest request = VirtualCardRequest.builder()
                .cardholderId(cardholderId)
                .cardProductId("pro_3fn6pv2ikshurn36dbd3iysyha")
                .lifetime(CardLifetime.builder()
                        .unit(LifetimeUnit.MONTHS)
                        .value(6)
                        .build())
                .reference("X-123456-N11")
                .displayName("John Kennedy")
                .isSingleUse(false)
                .activateCard(active)
                .build();

        final CardResponse cardResponse = blocking(() ->
                getIssuingCheckoutApi()
                        .issuingClient()
                        .createCard(request));

        assertNotNull(cardResponse);

        return cardResponse;
    }

    private AbstractCardControlResponse createCardControl(String cardId) {
        final AbstractCardControlRequest request = VelocityCardControlRequest.builder()
                .description("Max spend of 500€ per week for restaurants")
                .targetId(cardId)
                .velocityLimit(VelocityLimit.builder()
                        .amountLimit(500)
                        .velocityWindow(VelocityWindow.builder()
                                .type(VelocityWindowType.WEEKLY)
                                .build())
                        .build())
                .build();

        final AbstractCardControlResponse cardControlResponse = blocking(() ->
                getIssuingCheckoutApi()
                        .issuingClient()
                        .createControl(request));

        assertNotNull(cardControlResponse);

        return cardControlResponse;
    }

    private CheckoutApi getIssuingCheckoutApi() {
        return CheckoutSdk.builder()
                .oAuth()
                .clientCredentials(
                        requireNonNull(System.getenv("CHECKOUT_DEFAULT_OAUTH_ISSUING_CLIENT_ID")),
                        requireNonNull(System.getenv("CHECKOUT_DEFAULT_OAUTH_ISSUING_CLIENT_SECRET")))
                .scopes(OAuthScope.VAULT, OAuthScope.ISSUING_CLIENT, OAuthScope.ISSUING_CARD_MGMT,
                        OAuthScope.ISSUING_CONTROLS_READ, OAuthScope.ISSUING_CONTROLS_WRITE)
                .environment(Environment.SANDBOX)
                .build();
    }
}
