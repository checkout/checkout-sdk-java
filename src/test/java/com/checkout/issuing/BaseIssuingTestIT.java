package com.checkout.issuing;

import com.checkout.CheckoutApi;
import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import com.checkout.OAuthScope;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.DocumentType;
import com.checkout.issuing.cardholders.CardholderDocument;
import com.checkout.issuing.cardholders.CardholderRequest;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cardholders.CardholderType;
import com.checkout.issuing.cards.requests.create.CardLifetime;
import com.checkout.issuing.cards.requests.create.LifetimeUnit;
import com.checkout.issuing.cards.requests.create.VirtualCardRequest;
import com.checkout.issuing.cards.responses.CardResponse;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class BaseIssuingTestIT extends SandboxTestFixture {

    protected CheckoutApi issuingApi;

    public BaseIssuingTestIT() {
        super(PlatformType.DEFAULT);
        issuingApi = getIssuingCheckoutApi();
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

    protected CardholderResponse createCardholder() {
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
                issuingApi.issuingClient().createCardholder(cardholderRequest));

        assertNotNull(cardholderResponse);

        return cardholderResponse;
    }

    protected CardResponse createCard(final String cardholderId) {
        return this.createCard(cardholderId, false);
    }

    protected CardResponse createCard(final String cardholderId, final Boolean active) {
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
}
