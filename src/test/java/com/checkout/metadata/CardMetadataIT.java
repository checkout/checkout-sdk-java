package com.checkout.metadata;

import com.checkout.CardSourceHelper;
import com.checkout.CheckoutApi;
import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.TestHelper;
import com.checkout.metadata.card.CardMetadataFormatType;
import com.checkout.metadata.card.CardMetadataRequest;
import com.checkout.metadata.card.CardMetadataResponse;
import com.checkout.metadata.card.source.CardMetadataBinSource;
import com.checkout.metadata.card.source.CardMetadataCardSource;
import com.checkout.metadata.card.source.CardMetadataTokenSource;
import com.checkout.tokens.CardTokenRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CardMetadataIT extends SandboxTestFixture {
    CardMetadataIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    void shouldRequestMetadataCardForBinNumber() {

        final CardMetadataRequest metadataCardRequest = CardMetadataRequest.builder()
                .source(CardMetadataBinSource.builder()
                        .bin(CardSourceHelper.Visa.NUMBER.substring(0, 6))
                        .build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        makeCardMetadataRequest(metadataCardRequest);

    }

    @Test
    void shouldRequestCardMetadataForCardNumber() {

        final CardMetadataRequest metadataCardRequest = CardMetadataRequest.builder()
                .source(CardMetadataCardSource.builder()
                        .number(CardSourceHelper.Visa.NUMBER)
                        .build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        makeCardMetadataRequest(metadataCardRequest);
    }

    @Test
    void shouldRequestCardMetadataForToken() {

        final CardMetadataRequest metadataCardRequest = CardMetadataRequest.builder()
                .source(CardMetadataTokenSource.builder()
                        .token(createValidTokenRequest())
                        .build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        makeCardMetadataRequest(metadataCardRequest);
    }

    private void makeCardMetadataRequest(final CardMetadataRequest metadataCardRequest) {

        final CardMetadataResponse cardMetadataResponse = blocking(
                () -> checkoutApi.metadataClient().requestCardMetadata(metadataCardRequest));

        assertNotNull(cardMetadataResponse);
        assertNotNull(cardMetadataResponse.getBin());
        assertNotNull(cardMetadataResponse.getScheme());
        assertNotNull(cardMetadataResponse.getCardType());
        assertNotNull(cardMetadataResponse.getCardCategory());
        assertNotNull(cardMetadataResponse.getIssuerCountry());
        assertNotNull(cardMetadataResponse.getIssuerCountryName());
        assertNotNull(cardMetadataResponse.getProductId());
        assertNotNull(cardMetadataResponse.getProductType());
        assertEquals(200, cardMetadataResponse.getHttpStatusCode());
    }

    private String createValidTokenRequest() {

        final CheckoutApi checkoutApi = CheckoutSdk.builder().staticKeys()
                .publicKey(System.getenv("CHECKOUT_DEFAULT_PUBLIC_KEY"))
                .secretKey(System.getenv("CHECKOUT_DEFAULT_SECRET_KEY"))
                .environment(Environment.SANDBOX)
                .build();

        final CardTokenRequest cardTokenRequest = CardTokenRequest.builder()
                .number(TestCardSource.VISA.getNumber())
                .expiryMonth(TestCardSource.VISA.getExpiryMonth())
                .expiryYear(TestCardSource.VISA.getExpiryYear())
                .cvv(TestCardSource.VISA.getCvv())
                .billingAddress(TestHelper.createAddress())
                .phone(TestHelper.createPhone())
                .build();

        return blocking(() -> checkoutApi.tokensClient().requestCardToken(cardTokenRequest)).getToken();
    }
}
