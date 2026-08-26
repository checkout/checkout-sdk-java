package com.checkout.accounts;

import com.checkout.GsonSerializer;
import com.checkout.accounts.payout.schedule.DaySchedule;
import com.checkout.accounts.payout.schedule.request.ScheduleFrequencyMonthlyRequest;
import com.checkout.accounts.payout.schedule.request.ScheduleFrequencyWeeklyRequest;
import com.checkout.accounts.payout.schedule.request.UpdateScheduleRequest;
import com.checkout.accounts.payout.schedule.response.CurrencySchedule;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Covers the SaaS seller (ISV) payout schedule fields added by the 2026-08-05 spec.
 */
class PayoutScheduleIsvSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeIsvScheduleFields() {
        final UpdateScheduleRequest request = UpdateScheduleRequest.builder()
                .enabled(true)
                .threshold(100)
                .balanceMinimum(500L)
                .carryForwardEnabled(true)
                .paymentInstrumentId("ppi_w4jelhppmfiufdnatam37wrfc4")
                .recurrence(ScheduleFrequencyWeeklyRequest.builder()
                        .byDays(Collections.singletonList(DaySchedule.MONDAY))
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"balance_minimum\":500"), json);
        assertTrue(json.contains("\"carry_forward_enabled\":true"), json);
        assertTrue(json.contains("\"payment_instrument_id\":\"ppi_w4jelhppmfiufdnatam37wrfc4\""), json);
        assertTrue(json.contains("\"by_day\":[\"monday\"]"), json);
    }

    /**
     * A standard sub-entity has no balance minimum and no carry-forward, so neither may appear in
     * its request body. If they leaked in as nulls or zeros the API would read a standard schedule
     * as an ISV one.
     */
    @Test
    void shouldOmitIsvFieldsWhenUnset() {
        final UpdateScheduleRequest request = UpdateScheduleRequest.builder()
                .enabled(true)
                .threshold(100)
                .recurrence(ScheduleFrequencyMonthlyRequest.builder()
                        .byMonthDays(Arrays.asList(1, 15))
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertFalse(json.contains("balance_minimum"), json);
        assertFalse(json.contains("carry_forward_enabled"), json);
        assertFalse(json.contains("payment_instrument_id"), json);
        assertTrue(json.contains("\"by_month_day\":[1,15]"), json);
    }

    @Test
    void shouldDeserializeIsvScheduleFields() {
        final String json = "{\"enabled\":true,\"threshold\":100,\"balance_minimum\":500,"
                + "\"carry_forward_enabled\":true,"
                + "\"payment_instrument_id\":\"ppi_w4jelhppmfiufdnatam37wrfc4\","
                + "\"recurrence\":{\"frequency\":\"Weekly\",\"by_day\":[\"monday\"]}}";

        final CurrencySchedule schedule = serializer.fromJson(json, CurrencySchedule.class);

        assertTrue(schedule.getEnabled());
        assertEquals(100, schedule.getThreshold());
        assertEquals(500L, schedule.getBalanceMinimum());
        assertTrue(schedule.getCarryForwardEnabled());
        assertEquals("ppi_w4jelhppmfiufdnatam37wrfc4", schedule.getPaymentInstrumentId());
    }

    /**
     * A standard schedule omits both fields, and they must come back null rather than 0/false:
     * a caller cannot otherwise tell "not applicable" from "set to zero".
     */
    @Test
    void shouldLeaveIsvFieldsNullForAStandardSchedule() {
        final String json = "{\"enabled\":true,\"threshold\":100,"
                + "\"recurrence\":{\"frequency\":\"Daily\"}}";

        final CurrencySchedule schedule = serializer.fromJson(json, CurrencySchedule.class);

        assertNull(schedule.getBalanceMinimum());
        assertNull(schedule.getCarryForwardEnabled());
        assertNull(schedule.getPaymentInstrumentId());
    }

    /**
     * Frequency casing: the OpenAPI spec declares the frequency enum lowercase
     * (weekly, daily, monthly) but this SDK has serialized capitalized values
     * (Weekly, Daily, Monthly) since the model was introduced, with the
     * integration suite passing against the live API. Ruby shares the
     * capitalized values; .NET, Go, PHP and Python send lowercase. Until the
     * platform confirms which casing is canonical (likely a spec bug, or the
     * API accepts both), this test pins the value the SDK actually sends so a
     * change is a deliberate decision, not an accident.
     */
    @Test
    void shouldSerializeFrequencyCapitalizedAsToday() {
        final ScheduleFrequencyWeeklyRequest request = ScheduleFrequencyWeeklyRequest.builder()
                .byDays(Collections.singletonList(DaySchedule.MONDAY))
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"frequency\":\"Weekly\""), json);
    }

    /**
     * The spec's GetScheduleResponse nests everything under a per-currency
     * recurrence wrapper with the frequency in an inner schedule object. Every
     * typed SDK models the flat shape (asserted above) and their integration
     * suites pass, so the flat shape is treated as the real contract and the
     * spec wording as a spec bug (reported). This documents that a
     * spec-shaped payload does not deserialize at all: the recurrence wrapper
     * carries no frequency, so the ScheduleResponse adapter cannot dispatch
     * and throws. If this ever stops throwing, the API moved and the model
     * must follow.
     */
    @Test
    void shouldRejectASpecShapedResponse() {
        final String json = "{\"recurrence\":{\"enabled\":true,\"threshold\":100,"
                + "\"balance_minimum\":500,\"carry_forward_enabled\":true,"
                + "\"payment_instrument_id\":\"ppi_w4jelhppmfiufdnatam37wrfc4\","
                + "\"schedule\":{\"frequency\":\"weekly\",\"by_day\":[\"monday\"]}}}";

        assertThrows(com.google.gson.JsonParseException.class,
                () -> serializer.fromJson(json, CurrencySchedule.class));
    }
}
