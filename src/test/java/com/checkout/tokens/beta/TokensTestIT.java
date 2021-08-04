package com.checkout.tokens.beta;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TokensTestIT extends SandboxTestFixture {

    public TokensTestIT() {
        super(PlatformType.FOUR);
    }

    @Test
    public void shouldRequestToken() {

        final CardTokenRequest request = CardTokenRequest.builder()
                .number(TestCardSource.VISA.getNumber())
                .expiryMonth(TestCardSource.VISA.getExpiryMonth())
                .expiryYear(TestCardSource.VISA.getExpiryYear())
                .build();

        final CardTokenResponse response = blocking(getApiV2().tokensClient().requestAsync(request));

        assertNotNull(response);
        assertEquals("card", response.getType());
        assertTrue(response.getToken().startsWith("tok"));
        assertEquals(30, response.getToken().length());
        assertNotNull(response.getExpiresOn());
        assertTrue(response.getExpiresOn().isAfter(Instant.now()));
        assertEquals(6, response.getExpiryMonth().intValue());
        assertEquals(2025, response.getExpiryYear().intValue());
        assertEquals("VISA", response.getScheme());
        assertEquals("4242", response.getLast4());
        assertEquals("424242", response.getBin());
        assertEquals("CREDIT", response.getCardType());
        assertEquals("CONSUMER", response.getCardCategory());
        assertEquals("JPMORGAN CHASE BANK NA", response.getIssuer());
        assertEquals("US", response.getIssuerCountry());
        assertEquals("A", response.getProductId());
        assertEquals("Visa Traditional", response.getProductType());

    }

}