package com.checkout.metadata;

import com.checkout.CardSourceHelper;
import com.checkout.CheckoutApi;
import com.checkout.CheckoutApiImpl;
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

        final CardMetadataResponse cardMetadataResponse = blocking(
                () -> checkoutApi.metadataClient().requestCardMetadata(metadataCardRequest));
        
        validateResponse(cardMetadataResponse);

    }

    @Test
    void shouldRequestCardMetadataForCardNumber() {

        final CardMetadataRequest metadataCardRequest = CardMetadataRequest.builder()
                .source(CardMetadataCardSource.builder()
                        .number(CardSourceHelper.Visa.NUMBER)
                        .build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        final CardMetadataResponse cardMetadataResponse = blocking(
                () -> checkoutApi.metadataClient().requestCardMetadata(metadataCardRequest));
        
        validateResponse(cardMetadataResponse);
    }

    @Test
    void shouldRequestCardMetadataForToken() {
        final CheckoutApi checkoutApi = createCheckoutApi();
        final CardTokenRequest cardTokenRequest = createCardTokenRequest();

        final String token = blocking(() -> checkoutApi.tokensClient().requestCardToken(cardTokenRequest)).getToken();

        final CardMetadataRequest metadataCardRequest = CardMetadataRequest.builder()
                .source(CardMetadataTokenSource.builder()
                        .token(token)
                        .build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        final CardMetadataResponse cardMetadataResponse = blocking(
                () -> checkoutApi.metadataClient().requestCardMetadata(metadataCardRequest));
        
        validateResponse(cardMetadataResponse);
    }

    // Sync methods
    @Test
    void shouldRequestMetadataCardForBinNumberSync() {

        final CardMetadataRequest metadataCardRequest = CardMetadataRequest.builder()
                .source(CardMetadataBinSource.builder()
                        .bin(CardSourceHelper.Visa.NUMBER.substring(0, 6))
                        .build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        final CardMetadataResponse cardMetadataResponse = checkoutApi.metadataClient().requestCardMetadataSync(metadataCardRequest);
        
        validateResponse(cardMetadataResponse);

    }

    @Test
    void shouldRequestCardMetadataForCardNumberSync() {

        final CardMetadataRequest metadataCardRequest = CardMetadataRequest.builder()
                .source(CardMetadataCardSource.builder()
                        .number(CardSourceHelper.Visa.NUMBER)
                        .build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        final CardMetadataResponse cardMetadataResponse = checkoutApi.metadataClient().requestCardMetadataSync(metadataCardRequest);
        
        validateResponse(cardMetadataResponse);
    }

    @Test
    void shouldRequestCardMetadataForTokenSync() {
        final CheckoutApi checkoutApi = createCheckoutApi();
        final CardTokenRequest cardTokenRequest = createCardTokenRequest();

        final String token = checkoutApi.tokensClient().requestCardTokenSync(cardTokenRequest).getToken();

        final CardMetadataRequest metadataCardRequest = CardMetadataRequest.builder()
                .source(CardMetadataTokenSource.builder()
                        .token(token)
                        .build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        final CardMetadataResponse cardMetadataResponse = checkoutApi.metadataClient().requestCardMetadataSync(metadataCardRequest);
        
        validateResponse(cardMetadataResponse);
    }

    // Common methods
    private CheckoutApiImpl createCheckoutApi() {
        return CheckoutSdk.builder().staticKeys()
                .publicKey(System.getenv("CHECKOUT_DEFAULT_PUBLIC_KEY"))
                .secretKey(System.getenv("CHECKOUT_DEFAULT_SECRET_KEY"))
                .environment(Environment.SANDBOX)
                .build();
    }

    private CardTokenRequest createCardTokenRequest() {
        return CardTokenRequest.builder()
                .number(TestCardSource.VISA.getNumber())
                .expiryMonth(TestCardSource.VISA.getExpiryMonth())
                .expiryYear(TestCardSource.VISA.getExpiryYear())
                .cvv(TestCardSource.VISA.getCvv())
                .billingAddress(TestHelper.createAddress())
                .phone(TestHelper.createPhone())
                .build();
    }

    private void validateResponse(final CardMetadataResponse cardMetadataResponse)
    {
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
}
