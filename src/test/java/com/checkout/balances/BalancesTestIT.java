package com.checkout.balances;

import com.checkout.CheckoutApiException;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BalancesTestIT extends SandboxTestFixture {

    private static final String ENTITY_ID = "ent_kidtcgc3ge5unf4a5i6enhnr5m";

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


    /**
     * GET /entities/{entityId}/currency-accounts/{currencyAccountId}/top-up-instructions.
     *
     * <p>Top-ups are not enabled on the sandbox sub-accounts this suite has access to, so the
     * endpoint answers 403 ("top-ups aren't enabled for the sub-account") rather than 200.
     * Verified live on 2026-09-07 with the balances:top-up-instructions scope granted, which the
     * sandbox IdP does issue.
     *
     * <p>The test accepts either outcome, but only the outcomes the spec documents as "not
     * available here": 403 and 404. It still fails on 400 (malformed identifiers, i.e. the SDK
     * built the path wrongly) and on 401 (wrong authorization type), which are the two ways this
     * endpoint could actually be broken in the SDK.
     *
     * <p>Uses the synchronous method deliberately: the {@code blocking(...)} helper retries any
     * throwable 10 times before failing, which would turn a deterministic 403 into 10 wasted
     * calls and an unhelpful assertion error.
     */
    @Test
    void shouldRetrieveTopUpInstructions() {
        final BalancesResponse balances = checkoutApi.balancesClient()
                .retrieveEntityBalancesSync(ENTITY_ID, BalancesQuery.builder().withCurrencyAccountId(true).build());

        assertNotNull(balances);
        assertNotNull(balances.getData());

        // Take the first sub-account that reports an id. Requiring the entity to always have one
        // would fail this test for a reason unrelated to top-up instructions.
        final String currencyAccountId = balances.getData().stream()
                .map(CurrencyAccountBalance::getCurrencyAccountId)
                .filter(id -> id != null && !id.trim().isEmpty())
                .findFirst()
                .orElse(null);
        if (currencyAccountId == null) {
            return;
        }

        try {
            final TopUpInstructionsResponse instructions = checkoutApi.balancesClient()
                    .retrieveTopUpInstructionsSync(ENTITY_ID, currencyAccountId);

            assertNotNull(instructions);
            assertEquals(currencyAccountId, instructions.getCurrencyAccountId());
            assertNotNull(instructions.getCurrency());
            assertNotNull(instructions.getPaymentReference());
            assertNotNull(instructions.getBankDetails());

            // Assert only what the spec guarantees. TopUpBankDetails declares no required
            // properties, so an empty bank_details is a legal 200 body -- do not require a rail
            // to be present. Where a rail IS returned, its two required fields must be.
            for (final TopUpFundingDetails rail : Arrays.asList(
                    instructions.getBankDetails().getDomestic(),
                    instructions.getBankDetails().getInternational())) {
                if (rail == null) {
                    continue;
                }
                assertNotNull(rail.getBeneficiaryAccountName());
                assertNotNull(rail.getBankName());
            }
        } catch (final CheckoutApiException e) {
            // 403 = top-ups not enabled for the sub-account, or the credential lacks access.
            // 404 = sub-account not found, or it has no top-up instructions available.
            // Anything else means the SDK, not the environment, is at fault.
            assertTrue(Arrays.asList(403, 404).contains(e.getHttpStatusCode()),
                    "unexpected status " + e.getHttpStatusCode() + " from top-up instructions");
        }
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