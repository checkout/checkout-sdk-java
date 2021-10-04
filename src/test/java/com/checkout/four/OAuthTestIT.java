package com.checkout.four;

import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import com.checkout.FourOAuthScope;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.MarketplaceData;
import com.checkout.payments.four.CardSourceHelper;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.request.Payments;
import com.checkout.payments.four.request.source.RequestCardSource;
import com.checkout.payments.four.sender.RequestIndividualSender;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class OAuthTestIT extends SandboxTestFixture {

    private static final String OAUTH_AUTHORIZE_URL = "https://access.sandbox.checkout.com/connect/token";

    public OAuthTestIT() {
        super(PlatformType.FOUR_OAUTH);
    }

    @Test
    void shouldMakeOAuthCall() {

        final RequestCardSource source = RequestCardSource.builder()
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .ccv(CardSourceHelper.Visa.CCV)
                .stored(false)
                .build();

        final RequestIndividualSender sender = RequestIndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .address(Address.builder()
                        .addressLine1("Address Line 1")
                        .addressLine2("Address Line 2")
                        .city("City")
                        .country(CountryCode.GB)
                        .build())
                .build();

        final PaymentRequest request = Payments.card(source).individualSender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(10L)
                .currency(Currency.EUR)
                .processingChannelId("pc_a6dabcfa2o3ejghb3sjuotdzzy")
                .marketplace(MarketplaceData.builder().subEntityId("ent_ocw5i74vowfg2edpy66izhts2u").build())
                .build();

        assertNotNull(blocking(fourApi.paymentsClient().requestPayment(request)));

    }

    @Test
    void shouldInitAuthorization() {

        try {
            CheckoutSdk.fourSdk()
                    .oAuth()
                    .clientCredentials(
                            new URI(OAUTH_AUTHORIZE_URL),
                            "fake",
                            "fake")
                    .scopes(FourOAuthScope.GATEWAY)
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
            CheckoutSdk.fourSdk()
                    .oAuth()
                    .clientCredentials(
                            System.getenv("CHECKOUT_FOUR_OAUTH_CLIENT_ID"),
                            System.getenv("CHECKOUT_FOUR_OAUTH_CLIENT_SECRET"))
                    .scopes(FourOAuthScope.GATEWAY)
                    .uri(URI.create("https://test.checkout.com"))
                    .build();
            fail();
        } catch (final Exception e) {
            assertEquals("Invalid configuration. Please specify an Environment or a specific OAuth authorizationURI.", e.getMessage());
        }

    }

    @Test
    void shouldInstantiateCheckoutApiWithOAuth_defaultAuthorizeUrl() {

        final CheckoutApi checkoutApi = CheckoutSdk.fourSdk()
                .oAuth()
                .clientCredentials(
                        System.getenv("CHECKOUT_FOUR_OAUTH_CLIENT_ID"),
                        System.getenv("CHECKOUT_FOUR_OAUTH_CLIENT_SECRET"))
                .scopes(FourOAuthScope.GATEWAY)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi);

    }


    @Test
    void shouldInstantiateCheckoutApiWithOAuth_customAuthorizeUrl() throws URISyntaxException {

        final CheckoutApi checkoutApi = CheckoutSdk.fourSdk()
                .oAuth()
                .clientCredentials(
                        new URI(OAUTH_AUTHORIZE_URL),
                        System.getenv("CHECKOUT_FOUR_OAUTH_CLIENT_ID"),
                        System.getenv("CHECKOUT_FOUR_OAUTH_CLIENT_SECRET"))
                .scopes(FourOAuthScope.GATEWAY)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi);

    }

}
