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
                .recurrence(ScheduleFrequencyWeeklyRequest.builder()
                        .byDays(Collections.singletonList(DaySchedule.MONDAY))
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"balance_minimum\":500"), json);
        assertTrue(json.contains("\"carry_forward_enabled\":true"), json);
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
        assertTrue(json.contains("\"by_month_day\":[1,15]"), json);
    }

    @Test
    void shouldDeserializeIsvScheduleFields() {
        final String json = "{\"enabled\":true,\"threshold\":100,\"balance_minimum\":500,"
                + "\"carry_forward_enabled\":true,"
                + "\"recurrence\":{\"frequency\":\"Weekly\",\"by_day\":[\"monday\"]}}";

        final CurrencySchedule schedule = serializer.fromJson(json, CurrencySchedule.class);

        assertTrue(schedule.getEnabled());
        assertEquals(100, schedule.getThreshold());
        assertEquals(500L, schedule.getBalanceMinimum());
        assertTrue(schedule.getCarryForwardEnabled());
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
    }
}
