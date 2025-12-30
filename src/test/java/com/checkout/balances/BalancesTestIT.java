package com.checkout.balances;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BalancesTestIT extends SandboxTestFixture {

    BalancesTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    void shouldRetrieveEntityBalances() {
        final BalancesQuery query = createBalancesQuery();

        final BalancesResponse balancesResponse =
                blocking(() -> checkoutApi.balancesClient().retrieveEntityBalances("ent_kidtcgc3ge5unf4a5i6enhnr5m", query));

        validateBalancesResponse(balancesResponse);
    }

    // Synchronous test methods
    @Test
    void shouldRetrieveEntityBalancesSync() {
        final BalancesQuery query = createBalancesQuery();

        final BalancesResponse balancesResponse =
                checkoutApi.balancesClient().retrieveEntityBalancesSync("ent_kidtcgc3ge5unf4a5i6enhnr5m", query);

        validateBalancesResponse(balancesResponse);
    }

    // Common methods
    private BalancesQuery createBalancesQuery() {
        return BalancesQuery.builder()
                .query("currency:" + Currency.GBP)
                .build();
    }

    private void validateBalancesResponse(final BalancesResponse balancesResponse) {
        assertNotNull(balancesResponse);
        assertNotNull(balancesResponse.getData());
        for (final CurrencyAccountBalance balance : balancesResponse.getData()) {
            assertNotNull(balance.getDescriptor());
            assertNotNull(balance.getHoldingCurrency());
            assertNotNull(balance.getBalances());
        }
    }
}