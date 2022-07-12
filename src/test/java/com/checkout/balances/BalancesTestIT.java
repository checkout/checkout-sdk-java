package com.checkout.balances;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BalancesTestIT extends SandboxTestFixture {

    BalancesTestIT() {
        super(PlatformType.FOUR_OAUTH);
    }

    @Test
    void shouldRetrieveEntityBalances() {
        final BalancesQuery query = BalancesQuery.builder()
                .query("currency:" + Currency.GBP)
                .build();

        final BalancesResponse balancesResponse = blocking(() -> fourApi.balancesClient().retrieveEntityBalances("ent_kidtcgc3ge5unf4a5i6enhnr5m", query));
        assertNotNull(balancesResponse);
        assertNotNull(balancesResponse.getData());
        for (final CurrencyAccountBalance balance : balancesResponse.getData()) {
            assertNotNull(balance.getDescriptor());
            assertNotNull(balance.getHoldingCurrency());
            assertNotNull(balance.getBalances());
        }
    }

}