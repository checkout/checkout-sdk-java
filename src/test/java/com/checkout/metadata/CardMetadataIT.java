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

    // ─── BIN source ─────────────────────────────────────────────────────────

    @Test
    void shouldRequestMetadataCardForBinNumber() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataBinSource.builder()
                        .bin(CardSourceHelper.Visa.NUMBER.substring(0, 6))
                        .build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        final CardMetadataResponse response = blocking(
                () -> checkoutApi.metadataClient().requestCardMetadata(request));

        validateBasicResponse(response);
    }

    @Test
    void shouldRequestMetadataCardForBinNumberWithCardPayoutsFormat() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataBinSource.builder()
                        .bin(CardSourceHelper.Visa.NUMBER.substring(0, 6))
                        .build())
                .format(CardMetadataFormatType.CARD_PAYOUTS)
                .build();

        final CardMetadataResponse response = blocking(
                () -> checkoutApi.metadataClient().requestCardMetadata(request));

        validateBasicResponse(response);
    }

    @Test
    void shouldRequestMetadataCardForBinNumberWithReference() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataBinSource.builder()
                        .bin(CardSourceHelper.Visa.NUMBER.substring(0, 6))
                        .build())
                .format(CardMetadataFormatType.BASIC)
                .reference("ORD-5023-4E89")
                .build();

        final CardMetadataResponse response = blocking(
                () -> checkoutApi.metadataClient().requestCardMetadata(request));

        validateBasicResponse(response);
    }

    // ─── Card (PAN) source ───────────────────────────────────────────────────

    @Test
    void shouldRequestCardMetadataForCardNumber() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataCardSource.builder()
                        .number(CardSourceHelper.Visa.NUMBER)
                        .build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        final CardMetadataResponse response = blocking(
                () -> checkoutApi.metadataClient().requestCardMetadata(request));

        validateBasicResponse(response);
    }

    // ─── Token source ────────────────────────────────────────────────────────

    @Test
    void shouldRequestCardMetadataForToken() {
        final CheckoutApi staticKeyApi = createStaticKeyApi();
        final String token = blocking(
                () -> staticKeyApi.tokensClient().requestCardToken(createCardTokenRequest())).getToken();

        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataTokenSource.builder().token(token).build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        final CardMetadataResponse response = blocking(
                () -> checkoutApi.metadataClient().requestCardMetadata(request));

        validateBasicResponse(response);
    }

    // ─── Sync variants ──────────────────────────────────────────────────────

    @Test
    void shouldRequestMetadataCardForBinNumberSync() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataBinSource.builder()
                        .bin(CardSourceHelper.Visa.NUMBER.substring(0, 6))
                        .build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        final CardMetadataResponse response = checkoutApi.metadataClient().requestCardMetadataSync(request);

        validateBasicResponse(response);
    }

    @Test
    void shouldRequestCardMetadataForCardNumberSync() {
        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataCardSource.builder()
                        .number(CardSourceHelper.Visa.NUMBER)
                        .build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        final CardMetadataResponse response = checkoutApi.metadataClient().requestCardMetadataSync(request);

        validateBasicResponse(response);
    }

    @Test
    void shouldRequestCardMetadataForTokenSync() {
        final CheckoutApi staticKeyApi = createStaticKeyApi();
        final String token = staticKeyApi.tokensClient()
                .requestCardTokenSync(createCardTokenRequest()).getToken();

        final CardMetadataRequest request = CardMetadataRequest.builder()
                .source(CardMetadataTokenSource.builder().token(token).build())
                .format(CardMetadataFormatType.BASIC)
                .build();

        final CardMetadataResponse response = checkoutApi.metadataClient().requestCardMetadataSync(request);

        validateBasicResponse(response);
    }

    // ─── Helpers ────────────────────────────────────────────────────────────

    private CheckoutApiImpl createStaticKeyApi() {
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

    private void validateBasicResponse(final CardMetadataResponse response) {
        assertNotNull(response);
        assertNotNull(response.getBin());
        assertNotNull(response.getScheme());
        assertNotNull(response.getCardType());
        assertNotNull(response.getCardCategory());
        assertNotNull(response.getIssuerCountry());
        assertNotNull(response.getIssuerCountryName());
        assertNotNull(response.getProductId());
        assertNotNull(response.getProductType());
        assertEquals(200, response.getHttpStatusCode());
    }
}
