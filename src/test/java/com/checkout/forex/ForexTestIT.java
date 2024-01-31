package com.checkout.forex;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ForexTestIT extends SandboxTestFixture {


    ForexTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Disabled("Temporarily skipped")
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

    @Disabled("Skipping because processing_channel_id is invalid")
    @Test
    void shouldGetRates() {

        final RatesQueryFilter request = RatesQueryFilter.builder()
                .product("card_payouts")
                .source(ForexSource.VISA)
                .currencyPairs("GBPEUR,USDNOK,JPNCAD")
                .processChannelId("pc_abcdefghijklmnopqrstuvwxyz")
                .build();
        final RatesQueryResponse response = blocking(() -> checkoutApi.forexClient().getRates(request));

        assertNotNull(response);
        assertNotNull(response.getProduct());
        assertNotNull(response.getSource());
        assertEquals(request.getProduct(), response.getProduct());
        assertEquals(request.getSource(), response.getSource());
        assertNotNull(response.getRates());
    }

}
