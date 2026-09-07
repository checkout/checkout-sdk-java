package com.checkout.balances;

import com.checkout.GsonSerializer;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema validation tests for the balances package.
 *
 * <p>Covers the top-up instructions response
 * (GET /entities/{entityId}/currency-accounts/{currencyAccountId}/top-up-instructions) and the
 * entity balances response (GET /balances/{id}). Every value is taken from the field-level
 * {@code example} values in shared/swagger-latest.json; neither response schema carries a
 * top-level example.
 *
 * <p>The spec is explicit that neither funding rail is guaranteed: TopUpBankDetails declares no
 * {@code required} array, so domestic-only, international-only and an empty bank_details are all
 * legal 200 bodies. Each has its own test.
 */
class BalancesSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    private static final String BOTH_RAILS_JSON = "{"
            + "\"currency_account_id\":\"ca_g5y7d6jo4e2urgforcbf2ey5jm\","
            + "\"currency\":\"USD\","
            + "\"payment_reference\":\"TP-ABC123\","
            + "\"bank_details\":{"
            + "\"domestic\":" + fundingDetailsJson() + ","
            + "\"international\":" + fundingDetailsJson()
            + "}}";

    private static String fundingDetailsJson() {
        return "{"
                + "\"beneficiary_account_name\":\"Acme Inc\","
                + "\"beneficiary_address\":\"1 Example Street, Exampleville, EX, 00000, US\","
                + "\"bank_name\":\"Example Bank\","
                + "\"bank_address\":\"1 Example Street, Exampleville, EX, 00000, US\","
                + "\"account_number\":\"1234567890\","
                + "\"sort_code\":\"000000\","
                + "\"routing_number\":\"000000000\","
                + "\"iban\":\"GB00EXAM00000000000000\","
                + "\"swift_code\":\"TESTUS00XXX\""
                + "}";
    }

    private static TopUpFundingDetails createFullyPopulatedFundingDetails() {
        final TopUpFundingDetails details = new TopUpFundingDetails();
        details.setBeneficiaryAccountName("Acme Inc");
        details.setBeneficiaryAddress("1 Example Street, Exampleville, EX, 00000, US");
        details.setBankName("Example Bank");
        details.setBankAddress("1 Example Street, Exampleville, EX, 00000, US");
        details.setAccountNumber("1234567890");
        details.setSortCode("000000");
        details.setRoutingNumber("000000000");
        details.setIban("GB00EXAM00000000000000");
        details.setSwiftCode("TESTUS00XXX");
        return details;
    }

    private static void assertFullyPopulatedFundingDetails(final TopUpFundingDetails details) {
        assertNotNull(details);
        assertEquals("Acme Inc", details.getBeneficiaryAccountName());
        assertEquals("1 Example Street, Exampleville, EX, 00000, US", details.getBeneficiaryAddress());
        assertEquals("Example Bank", details.getBankName());
        assertEquals("1 Example Street, Exampleville, EX, 00000, US", details.getBankAddress());
        assertEquals("1234567890", details.getAccountNumber());
        assertEquals("000000", details.getSortCode());
        assertEquals("000000000", details.getRoutingNumber());
        assertEquals("GB00EXAM00000000000000", details.getIban());
        assertEquals("TESTUS00XXX", details.getSwiftCode());
    }

    // ------------------------------------------------------------------------
    // TopUpInstructionsResponse / TopUpBankDetails / TopUpFundingDetails
    // ------------------------------------------------------------------------

    @Test
    void shouldSerializeTopUpInstructionsResponseWithRequiredFields() {
        final TopUpInstructionsResponse response = new TopUpInstructionsResponse();
        response.setCurrencyAccountId("ca_g5y7d6jo4e2urgforcbf2ey5jm");
        response.setCurrency(Currency.USD);
        response.setPaymentReference("TP-ABC123");
        response.setBankDetails(new TopUpBankDetails());

        assertDoesNotThrow(() -> serializer.toJson(response));
    }

    @Test
    void shouldSerializeTopUpInstructionsResponseToSnakeCase() {
        final TopUpBankDetails bankDetails = new TopUpBankDetails();
        bankDetails.setDomestic(createFullyPopulatedFundingDetails());
        bankDetails.setInternational(createFullyPopulatedFundingDetails());

        final TopUpInstructionsResponse response = new TopUpInstructionsResponse();
        response.setCurrencyAccountId("ca_g5y7d6jo4e2urgforcbf2ey5jm");
        response.setCurrency(Currency.USD);
        response.setPaymentReference("TP-ABC123");
        response.setBankDetails(bankDetails);

        final String json = serializer.toJson(response);

        assertNotNull(json);
        assertTrue(json.contains("\"currency_account_id\":\"ca_g5y7d6jo4e2urgforcbf2ey5jm\""));
        assertTrue(json.contains("\"currency\":\"USD\""));
        assertTrue(json.contains("\"payment_reference\":\"TP-ABC123\""));
        assertTrue(json.contains("\"bank_details\""));
        assertTrue(json.contains("\"domestic\""));
        assertTrue(json.contains("\"international\""));
        assertTrue(json.contains("\"beneficiary_account_name\":\"Acme Inc\""));
        assertTrue(json.contains("\"beneficiary_address\":\"1 Example Street, Exampleville, EX, 00000, US\""));
        assertTrue(json.contains("\"bank_name\":\"Example Bank\""));
        assertTrue(json.contains("\"bank_address\":\"1 Example Street, Exampleville, EX, 00000, US\""));
        assertTrue(json.contains("\"account_number\":\"1234567890\""));
        assertTrue(json.contains("\"sort_code\":\"000000\""));
        assertTrue(json.contains("\"routing_number\":\"000000000\""));
        assertTrue(json.contains("\"iban\":\"GB00EXAM00000000000000\""));
        assertTrue(json.contains("\"swift_code\":\"TESTUS00XXX\""));
    }

    @Test
    void shouldDeserializeSpecExampleForTopUpInstructionsResponse() {
        final TopUpInstructionsResponse response =
                serializer.fromJson(BOTH_RAILS_JSON, TopUpInstructionsResponse.class);

        assertNotNull(response);
        assertEquals("ca_g5y7d6jo4e2urgforcbf2ey5jm", response.getCurrencyAccountId());
        assertEquals(Currency.USD, response.getCurrency());
        assertEquals("TP-ABC123", response.getPaymentReference());
        assertNotNull(response.getBankDetails());
        assertFullyPopulatedFundingDetails(response.getBankDetails().getDomestic());
        assertFullyPopulatedFundingDetails(response.getBankDetails().getInternational());
    }

    @Test
    void shouldRoundTripTopUpInstructionsResponse() {
        final TopUpBankDetails bankDetails = new TopUpBankDetails();
        bankDetails.setDomestic(createFullyPopulatedFundingDetails());
        bankDetails.setInternational(createFullyPopulatedFundingDetails());

        final TopUpInstructionsResponse original = new TopUpInstructionsResponse();
        original.setCurrencyAccountId("ca_g5y7d6jo4e2urgforcbf2ey5jm");
        original.setCurrency(Currency.USD);
        original.setPaymentReference("TP-ABC123");
        original.setBankDetails(bankDetails);

        final TopUpInstructionsResponse deserialized =
                serializer.fromJson(serializer.toJson(original), TopUpInstructionsResponse.class);

        assertEquals(original.getCurrencyAccountId(), deserialized.getCurrencyAccountId());
        assertEquals(original.getCurrency(), deserialized.getCurrency());
        assertEquals(original.getPaymentReference(), deserialized.getPaymentReference());
        assertFullyPopulatedFundingDetails(deserialized.getBankDetails().getDomestic());
        assertFullyPopulatedFundingDetails(deserialized.getBankDetails().getInternational());
    }

    @Test
    void shouldDeserializeDomesticOnlyTopUpInstructionsResponse() {
        // A United States domestic rail, per "Returned for United States domestic transfers"
        // on routing_number.
        final String json = "{"
                + "\"currency_account_id\":\"ca_g5y7d6jo4e2urgforcbf2ey5jm\","
                + "\"currency\":\"USD\","
                + "\"payment_reference\":\"TP-ABC123\","
                + "\"bank_details\":{\"domestic\":{"
                + "\"beneficiary_account_name\":\"Acme Inc\","
                + "\"bank_name\":\"Example Bank\","
                + "\"account_number\":\"1234567890\","
                + "\"routing_number\":\"000000000\""
                + "}}}";

        final TopUpInstructionsResponse response =
                serializer.fromJson(json, TopUpInstructionsResponse.class);

        assertNotNull(response.getBankDetails());
        assertNull(response.getBankDetails().getInternational());
        assertNotNull(response.getBankDetails().getDomestic());
        assertEquals("Acme Inc", response.getBankDetails().getDomestic().getBeneficiaryAccountName());
        assertEquals("Example Bank", response.getBankDetails().getDomestic().getBankName());
        assertEquals("1234567890", response.getBankDetails().getDomestic().getAccountNumber());
        assertEquals("000000000", response.getBankDetails().getDomestic().getRoutingNumber());
        assertNull(response.getBankDetails().getDomestic().getSortCode());
        assertNull(response.getBankDetails().getDomestic().getIban());
        assertNull(response.getBankDetails().getDomestic().getSwiftCode());
    }

    @Test
    void shouldDeserializeInternationalOnlyTopUpInstructionsResponse() {
        // An international rail, per "Returned for international transfers" on swift_code.
        final String json = "{"
                + "\"currency_account_id\":\"ca_g5y7d6jo4e2urgforcbf2ey5jm\","
                + "\"currency\":\"USD\","
                + "\"payment_reference\":\"TP-ABC123\","
                + "\"bank_details\":{\"international\":{"
                + "\"beneficiary_account_name\":\"Acme Inc\","
                + "\"bank_name\":\"Example Bank\","
                + "\"iban\":\"GB00EXAM00000000000000\","
                + "\"swift_code\":\"TESTUS00XXX\""
                + "}}}";

        final TopUpInstructionsResponse response =
                serializer.fromJson(json, TopUpInstructionsResponse.class);

        assertNotNull(response.getBankDetails());
        assertNull(response.getBankDetails().getDomestic());
        assertNotNull(response.getBankDetails().getInternational());
        assertEquals("Acme Inc", response.getBankDetails().getInternational().getBeneficiaryAccountName());
        assertEquals("Example Bank", response.getBankDetails().getInternational().getBankName());
        assertEquals("GB00EXAM00000000000000", response.getBankDetails().getInternational().getIban());
        assertEquals("TESTUS00XXX", response.getBankDetails().getInternational().getSwiftCode());
        assertNull(response.getBankDetails().getInternational().getAccountNumber());
        assertNull(response.getBankDetails().getInternational().getRoutingNumber());
        assertNull(response.getBankDetails().getInternational().getSortCode());
    }

    @Test
    void shouldDeserializeEmptyBankDetailsForTopUpInstructionsResponse() {
        // TopUpBankDetails declares no required properties, so an empty object is legal.
        final String json = "{"
                + "\"currency_account_id\":\"ca_g5y7d6jo4e2urgforcbf2ey5jm\","
                + "\"currency\":\"USD\","
                + "\"payment_reference\":\"TP-ABC123\","
                + "\"bank_details\":{}"
                + "}";

        final TopUpInstructionsResponse response =
                serializer.fromJson(json, TopUpInstructionsResponse.class);

        assertNotNull(response);
        assertNotNull(response.getBankDetails());
        assertNull(response.getBankDetails().getDomestic());
        assertNull(response.getBankDetails().getInternational());
    }

    @Test
    void shouldOmitUnsetOptionalFundingFields() {
        final TopUpFundingDetails domestic = new TopUpFundingDetails();
        domestic.setBeneficiaryAccountName("Acme Inc");
        domestic.setBankName("Example Bank");

        final TopUpBankDetails bankDetails = new TopUpBankDetails();
        bankDetails.setDomestic(domestic);

        final TopUpInstructionsResponse response = new TopUpInstructionsResponse();
        response.setCurrencyAccountId("ca_g5y7d6jo4e2urgforcbf2ey5jm");
        response.setCurrency(Currency.USD);
        response.setPaymentReference("TP-ABC123");
        response.setBankDetails(bankDetails);

        final String json = serializer.toJson(response);

        assertTrue(json.contains("\"beneficiary_account_name\":\"Acme Inc\""));
        assertTrue(json.contains("\"bank_name\":\"Example Bank\""));
        assertFalse(json.contains("international"));
        assertFalse(json.contains("sort_code"));
        assertFalse(json.contains("routing_number"));
        assertFalse(json.contains("iban"));
        assertFalse(json.contains("swift_code"));
        assertFalse(json.contains("beneficiary_address"));
        assertFalse(json.contains("bank_address"));
        assertFalse(json.contains("account_number"));
    }

    // ------------------------------------------------------------------------
    // BalancesResponse / CurrencyAccountBalance / Balances / CollateralBreakdown
    //
    // Added when Balance.operational was found missing from the SDK during the INT-1692 review;
    // review-integrity.mdc section 9 requires a serialization test for a new field on an
    // existing class.
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializeSpecExampleForBalancesResponse() {
        final String json = "{\"data\":[{"
                + "\"currency_account_id\":\"ca_g5y7d6jo4e2urgforcbf2ey5jm\","
                + "\"descriptor\":\"Revenue Account 1\","
                + "\"holding_currency\":\"USD\","
                + "\"balances_as_of\":\"2026-05-06T13:59:59Z\","
                + "\"balances\":{"
                + "\"pending\":23000,"
                + "\"available\":50000,"
                + "\"payable\":2700,"
                + "\"collateral\":6000,"
                + "\"operational\":7000,"
                + "\"collateral_breakdown\":{\"fixed_reserve\":4000,\"rolling_reserve\":2000}"
                + "}}]}";

        final BalancesResponse response = serializer.fromJson(json, BalancesResponse.class);

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());

        final CurrencyAccountBalance balance = response.getData().get(0);
        assertEquals("ca_g5y7d6jo4e2urgforcbf2ey5jm", balance.getCurrencyAccountId());
        assertEquals("Revenue Account 1", balance.getDescriptor());
        assertEquals(Currency.USD, balance.getHoldingCurrency());
        assertEquals(Instant.parse("2026-05-06T13:59:59Z"), balance.getBalancesAsOf());

        assertNotNull(balance.getBalances());
        assertEquals(Long.valueOf(23000L), balance.getBalances().getPending());
        assertEquals(Long.valueOf(50000L), balance.getBalances().getAvailable());
        assertEquals(Long.valueOf(2700L), balance.getBalances().getPayable());
        assertEquals(Long.valueOf(6000L), balance.getBalances().getCollateral());
        assertEquals(Long.valueOf(7000L), balance.getBalances().getOperational());
        assertNotNull(balance.getBalances().getCollateralBreakdown());
        assertEquals(Long.valueOf(4000L), balance.getBalances().getCollateralBreakdown().getFixedReserve());
        assertEquals(Long.valueOf(2000L), balance.getBalances().getCollateralBreakdown().getRollingReserve());
    }

    @Test
    void shouldRoundTripBalances() {
        final CollateralBreakdown breakdown = new CollateralBreakdown();
        breakdown.setFixedReserve(4000L);
        breakdown.setRollingReserve(2000L);

        final Balances original = new Balances();
        original.setPending(23000L);
        original.setAvailable(50000L);
        original.setPayable(2700L);
        original.setCollateral(6000L);
        original.setOperational(7000L);
        original.setCollateralBreakdown(breakdown);

        final String json = serializer.toJson(original);
        final Balances deserialized = serializer.fromJson(json, Balances.class);

        assertTrue(json.contains("\"operational\":7000"));
        assertTrue(json.contains("\"collateral_breakdown\""));
        assertEquals(original.getPending(), deserialized.getPending());
        assertEquals(original.getAvailable(), deserialized.getAvailable());
        assertEquals(original.getPayable(), deserialized.getPayable());
        assertEquals(original.getCollateral(), deserialized.getCollateral());
        assertEquals(original.getOperational(), deserialized.getOperational());
        assertEquals(breakdown.getFixedReserve(), deserialized.getCollateralBreakdown().getFixedReserve());
        assertEquals(breakdown.getRollingReserve(), deserialized.getCollateralBreakdown().getRollingReserve());
    }
}
