package com.checkout;

import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.AccountHolderIdentificationType;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.MarketplaceData;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.sender.PaymentIndividualSender;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static java.net.URI.create;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

class OAuthTestIT extends SandboxTestFixture {

    private static final String OAUTH_AUTHORIZE_URL = "https://access.sandbox.checkout.com/connect/token";

    public OAuthTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    void shouldMakeOAuthCall() {

        final RequestCardSource source = RequestCardSource.builder()
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .cvv(CardSourceHelper.Visa.CVV)
                .stored(false)
                .build();

        final PaymentIndividualSender sender = PaymentIndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .address(Address.builder()
                        .addressLine1("Address Line 1")
                        .addressLine2("Address Line 2")
                        .city("City")
                        .country(CountryCode.GB)
                        .build())
                .identification(AccountHolderIdentification.builder()
                        .type(AccountHolderIdentificationType.DRIVING_LICENSE)
                        .number("1234")
                        .issuingCountry(CountryCode.GB)
                        .build())
                .build();

        final PaymentRequest request = PaymentRequest.builder()
                .source(source)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(10L)
                .currency(Currency.EUR)
                .processingChannelId("pc_a6dabcfa2o3ejghb3sjuotdzzy")
                .marketplace(MarketplaceData.builder().subEntityId("ent_ocw5i74vowfg2edpy66izhts2u").build())
                .build();

        assertNotNull(blocking(() -> checkoutApi.paymentsClient().requestPayment(request)));

    }

    @Test
    void shouldInitAuthorization() {

        try {
            CheckoutSdk.builder()
                    .oAuth()
                    .clientCredentials(
                            new URI(OAUTH_AUTHORIZE_URL),
                            "fake",
                            "fake")
                    .scopes(OAuthScope.GATEWAY)
                    .environment(Environment.SANDBOX)
                    .build();
            fail();
        } catch (final Exception e) {
            assertEquals("OAuth client_credentials authentication failed with error: invalid_client", e.getMessage());
        }

    }

    @Test
    void shouldFailInitAuthorization() {

        try {
            CheckoutSdk.builder()
                    .oAuth()
                    .clientCredentials(
                            System.getenv("CHECKOUT_DEFAULT_OAUTH_CLIENT_ID"),
                            System.getenv("CHECKOUT_DEFAULT_OAUTH_CLIENT_SECRET"))
                    .scopes(OAuthScope.GATEWAY)
                    .build();
            fail();
        } catch (final Exception e) {
            assertEquals("environment must be specified", e.getMessage());
        }

    }

    @Test
    void shouldInstantiateCheckoutApiWithOAuth_defaultAuthorizeUrl() {

        final CheckoutApi checkoutApi = CheckoutSdk.builder()
                .oAuth()
                .clientCredentials(
                        System.getenv("CHECKOUT_DEFAULT_OAUTH_CLIENT_ID"),
                        System.getenv("CHECKOUT_DEFAULT_OAUTH_CLIENT_SECRET"))
                .scopes(OAuthScope.GATEWAY)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi);

    }


    @Test
    void shouldInstantiateCheckoutApiWithOAuth_customAuthorizeUrl() throws URISyntaxException {

        final CheckoutApi checkoutApi = CheckoutSdk.builder()
                .oAuth()
                .clientCredentials(
                        new URI(OAUTH_AUTHORIZE_URL),
                        System.getenv("CHECKOUT_DEFAULT_OAUTH_CLIENT_ID"),
                        System.getenv("CHECKOUT_DEFAULT_OAUTH_CLIENT_SECRET"))
                .scopes(OAuthScope.GATEWAY)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi);

    }

    @Test
    void shouldFailInitAuthorizationWithCustomEnvironment() {

        try {
            CheckoutSdk.builder()
                    .oAuth()
                    .clientCredentials(
                            System.getenv("CHECKOUT_DEFAULT_OAUTH_CLIENT_ID"),
                            System.getenv("CHECKOUT_DEFAULT_OAUTH_CLIENT_SECRET"))
                    .scopes(OAuthScope.GATEWAY)
                    .environment(CustomEnvironment.builder()
                            .oAuthAuthorizationApi(create("https://the.oauth.uri/connect/token"))
                            .build())
                    .build();
            fail();
        } catch (final Exception e) {
            assertEquals("OAuth client_credentials authentication failed", e.getMessage());
        }

    }

}
