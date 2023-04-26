package com.checkout.issuing;

import com.checkout.CheckoutApi;
import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import com.checkout.OAuthScope;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.DocumentType;
import com.checkout.issuing.cardholders.*;
import com.checkout.issuing.cards.AbstractCardDetailsResponse;
import org.junit.jupiter.api.*;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IssuingTestIT extends SandboxTestFixture {

    private CardholderResponse cardholder;

    public IssuingTestIT() { super(PlatformType.DEFAULT_OAUTH); }

    @BeforeAll
    void setUp() {
        cardholder = createCardholder();
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
                assertEquals(cardholder.getId(), card.getId());
                assertEquals("cli_p6jeowdtuxku3azxgt2qa7kq7a", card.getClientId());
            }
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
