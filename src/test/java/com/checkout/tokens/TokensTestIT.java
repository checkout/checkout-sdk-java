package com.checkout.tokens;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokensTestIT extends SandboxTestFixture {

    TokensTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldRequestCardToken() {
        final CardTokenRequest request = CardTokenRequest.builder()
                .number(TestCardSource.VISA.getNumber())
                .expiryMonth(TestCardSource.VISA.getExpiryMonth())
                .expiryYear(TestCardSource.VISA.getExpiryYear())
                .build();
        final CardTokenResponse response = blocking(() -> checkoutApi.tokensClient().requestCardToken(request));
        
        validateCardTokenResponse(response);
    }

    @Disabled("Sandbox token metadata lookup currently returns 404 for generated tokens in this environment")
    @Test
    void shouldGetTokenMetadata() {
        final CardTokenRequest request = CardTokenRequest.builder()
                .number(TestCardSource.VISA.getNumber())
                .expiryMonth(TestCardSource.VISA.getExpiryMonth())
                .expiryYear(TestCardSource.VISA.getExpiryYear())
                .build();
        final CardTokenResponse tokenResponse = blocking(() -> checkoutApi.tokensClient().requestCardToken(request));

        final TokenMetadataResponse metadataResponse = blocking(() -> checkoutApi.tokensClient().getTokenMetadata(tokenResponse.getToken()));

        validateTokenMetadataResponse(metadataResponse, tokenResponse.getToken());
    }

    // Synchronous test methods
    @Test
    void shouldRequestCardTokenSync() {
        final CardTokenRequest request = CardTokenRequest.builder()
                .number(TestCardSource.VISA.getNumber())
                .expiryMonth(TestCardSource.VISA.getExpiryMonth())
                .expiryYear(TestCardSource.VISA.getExpiryYear())
                .build();
        final CardTokenResponse response = checkoutApi.tokensClient().requestCardTokenSync(request);
        
        validateCardTokenResponse(response);
    }

    @Disabled("Sandbox token metadata lookup currently returns 404 for generated tokens in this environment")
    @Test
    void shouldGetTokenMetadataSync() {
        final CardTokenRequest request = CardTokenRequest.builder()
                .number(TestCardSource.VISA.getNumber())
                .expiryMonth(TestCardSource.VISA.getExpiryMonth())
                .expiryYear(TestCardSource.VISA.getExpiryYear())
                .build();
        final CardTokenResponse tokenResponse = checkoutApi.tokensClient().requestCardTokenSync(request);

        final TokenMetadataResponse metadataResponse = checkoutApi.tokensClient().getTokenMetadataSync(tokenResponse.getToken());

        validateTokenMetadataResponse(metadataResponse, tokenResponse.getToken());
    }

    // Common validation methods
    private void validateCardTokenResponse(CardTokenResponse response) {
        assertNotNull(response);
        assertEquals(TokenType.CARD, response.getType());
        assertTrue(response.getToken().startsWith("tok"));
        assertEquals(30, response.getToken().length());
        assertNotNull(response.getExpiresOn());
        assertTrue(response.getExpiresOn().isAfter(Instant.now()));
        assertEquals(6, response.getExpiryMonth().intValue());
        assertEquals(2025, response.getExpiryYear().intValue());
        assertEquals("VISA", response.getScheme());
        assertEquals("4242", response.getLast4());
        assertEquals("42424242", response.getBin());
        assertEquals(CardType.CREDIT, response.getCardType());
        assertEquals(CardCategory.CONSUMER, response.getCardCategory());
        //assertEquals("JPMORGAN CHASE BANK NA", response.getIssuer());
        //assertEquals(CountryCode.US, response.getIssuerCountry());
        //assertEquals("A", response.getProductId());
        //assertEquals("Visa Traditional", response.getProductType());
    }

    private void validateTokenMetadataResponse(TokenMetadataResponse response, String tokenId) {
        assertNotNull(response);
        assertEquals(tokenId, response.getToken());
        assertEquals("card", response.getType());
        assertNotNull(response.getExpiresOn());
        assertEquals(6, response.getExpiryMonth().intValue());
        assertEquals(2025, response.getExpiryYear().intValue());
        assertEquals("VISA", response.getScheme());
        assertEquals("4242", response.getLast4());
        assertNotNull(response.getBin());
        assertNotNull(response.getCardType());
        assertNotNull(response.getCardCategory());
    }

}