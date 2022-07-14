package com.checkout.forex;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ForexTestIT extends SandboxTestFixture {


    ForexTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    void shouldRequestQuote() {

        final QuoteRequest request = QuoteRequest.builder()
                .sourceCurrency(Currency.GBP)
                .sourceAmount(30000L)
                .destinationCurrency(Currency.USD)
                .processChannelId("pc_abcdefghijklmnopqrstuvwxyz")
                .build();
        final QuoteResponse response = blocking(() -> checkoutApi.forexClient().requestQuote(request));

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(request.getSourceCurrency(), response.getSourceCurrency());
        assertEquals(request.getSourceAmount(), response.getSourceAmount());
        assertEquals(request.getDestinationCurrency(), response.getDestinationCurrency());
        assertNotNull(response.getDestinationAmount());
        assertNotNull(response.getRate());
        assertNotNull(response.getExpiresOn());
        assertFalse(response.isSingleUse());
    }

}
