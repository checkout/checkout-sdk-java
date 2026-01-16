package com.checkout.accounts;

import com.checkout.CheckoutApi;
import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import com.checkout.OAuthScope;
import com.checkout.accounts.payout.schedule.DaySchedule;
import com.checkout.accounts.payout.schedule.request.ScheduleFrequencyDailyRequest;
import com.checkout.accounts.payout.schedule.request.ScheduleFrequencyMonthlyRequest;
import com.checkout.accounts.payout.schedule.request.ScheduleFrequencyWeeklyRequest;
import com.checkout.accounts.payout.schedule.request.UpdateScheduleRequest;
import com.checkout.accounts.payout.schedule.response.CurrencySchedule;
import com.checkout.accounts.payout.schedule.response.GetScheduleResponse;
import com.checkout.accounts.payout.schedule.response.ScheduleFrequencyDailyResponse;
import com.checkout.accounts.payout.schedule.response.ScheduleFrequencyMonthlyResponse;
import com.checkout.accounts.payout.schedule.response.ScheduleFrequencyWeeklyResponse;
import com.checkout.accounts.payout.schedule.response.VoidResponse;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountsPayoutSchedulesIT {

    @Test
    @Disabled("not available")
    void shouldUpdateAndRetrieveWeeklyPayoutSchedules() throws ExecutionException, InterruptedException {
        final UpdateScheduleRequest updateScheduleRequest = buildWeeklyScheduleRequest();

        final VoidResponse scheduleResponse = getPayoutSchedulesCheckoutApi().accountsClient()
                .updatePayoutSchedule("ent_sdioy6bajpzxyl3utftdp7legq", Currency.USD, updateScheduleRequest).get();

        validateVoidResponse(scheduleResponse);

        final GetScheduleResponse GetScheduleResponse = getPayoutSchedulesCheckoutApi().accountsClient()
                .retrievePayoutSchedule("ent_sdioy6bajpzxyl3utftdp7legq").get();

        validateWeeklyScheduleResponse(GetScheduleResponse);
    }

    @Test
    @Disabled("not available")
    void shouldUpdateAndRetrieveDailyPayoutSchedules() throws ExecutionException, InterruptedException {
        final UpdateScheduleRequest updateScheduleRequest = buildDailyScheduleRequest();

        final VoidResponse scheduleResponse = getPayoutSchedulesCheckoutApi().accountsClient()
                .updatePayoutSchedule("ent_sdioy6bajpzxyl3utftdp7legq", Currency.USD, updateScheduleRequest).get();

        validateVoidResponse(scheduleResponse);

        final GetScheduleResponse GetScheduleResponse = getPayoutSchedulesCheckoutApi().accountsClient()
                .retrievePayoutSchedule("ent_sdioy6bajpzxyl3utftdp7legq").get();

        validateDailyScheduleResponse(GetScheduleResponse);
    }

    @Test
    @Disabled("not available")
    void shouldUpdateAndRetrieveMonthlyPayoutSchedules() throws ExecutionException, InterruptedException {
        final UpdateScheduleRequest updateScheduleRequest = buildMonthlyScheduleRequest();

        final VoidResponse scheduleResponse = getPayoutSchedulesCheckoutApi().accountsClient()
                .updatePayoutSchedule("ent_sdioy6bajpzxyl3utftdp7legq", Currency.USD, updateScheduleRequest).get();

        validateVoidResponse(scheduleResponse);

        final GetScheduleResponse GetScheduleResponse = getPayoutSchedulesCheckoutApi().accountsClient()
                .retrievePayoutSchedule("ent_sdioy6bajpzxyl3utftdp7legq").get();

        validateMonthlyScheduleResponse(GetScheduleResponse);
    }

    // Synchronous methods
    @Test
    @Disabled("not available")
    void shouldUpdateAndRetrieveWeeklyPayoutSchedulesSync() {
        final UpdateScheduleRequest updateScheduleRequest = buildWeeklyScheduleRequest();

        final VoidResponse scheduleResponse = getPayoutSchedulesCheckoutApi().accountsClient()
                .updatePayoutScheduleSync("ent_sdioy6bajpzxyl3utftdp7legq", Currency.USD, updateScheduleRequest);

        validateVoidResponse(scheduleResponse);

        final GetScheduleResponse GetScheduleResponse = getPayoutSchedulesCheckoutApi().accountsClient()
                .retrievePayoutScheduleSync("ent_sdioy6bajpzxyl3utftdp7legq");

        validateWeeklyScheduleResponse(GetScheduleResponse);
    }

    @Test
    @Disabled("not available")
    void shouldUpdateAndRetrieveDailyPayoutSchedulesSync() {
        final UpdateScheduleRequest updateScheduleRequest = buildDailyScheduleRequest();

        final VoidResponse scheduleResponse = getPayoutSchedulesCheckoutApi().accountsClient()
                .updatePayoutScheduleSync("ent_sdioy6bajpzxyl3utftdp7legq", Currency.USD, updateScheduleRequest);

        validateVoidResponse(scheduleResponse);

        final GetScheduleResponse GetScheduleResponse = getPayoutSchedulesCheckoutApi().accountsClient()
                .retrievePayoutScheduleSync("ent_sdioy6bajpzxyl3utftdp7legq");

        validateDailyScheduleResponse(GetScheduleResponse);
    }

    @Test
    @Disabled("not available")
    void shouldUpdateAndRetrieveMonthlyPayoutSchedulesSync() {
        final UpdateScheduleRequest updateScheduleRequest = buildMonthlyScheduleRequest();

        final VoidResponse scheduleResponse = getPayoutSchedulesCheckoutApi().accountsClient()
                .updatePayoutScheduleSync("ent_sdioy6bajpzxyl3utftdp7legq", Currency.USD, updateScheduleRequest);

        validateVoidResponse(scheduleResponse);

        final GetScheduleResponse GetScheduleResponse = getPayoutSchedulesCheckoutApi().accountsClient()
                .retrievePayoutScheduleSync("ent_sdioy6bajpzxyl3utftdp7legq");

        validateMonthlyScheduleResponse(GetScheduleResponse);
    }

    // Common methods
    private UpdateScheduleRequest buildWeeklyScheduleRequest() {
        return UpdateScheduleRequest.builder()
                .enabled(true)
                .threshold(1000)
                .recurrence(ScheduleFrequencyWeeklyRequest.builder()
                        .byDays(Collections.singletonList(DaySchedule.MONDAY))
                        .build())
                .build();
    }

    private UpdateScheduleRequest buildDailyScheduleRequest() {
        return UpdateScheduleRequest.builder()
                .enabled(true)
                .threshold(1000)
                .recurrence(new ScheduleFrequencyDailyRequest())
                .build();
    }

    private UpdateScheduleRequest buildMonthlyScheduleRequest() {
        return UpdateScheduleRequest.builder()
                .enabled(true)
                .threshold(1000)
                .recurrence(ScheduleFrequencyMonthlyRequest.builder()
                        .byMonthDays(Collections.singletonList(15))
                        .build())
                .build();
    }

    private void validateVoidResponse(final VoidResponse scheduleResponse) {
        assertNotNull(scheduleResponse);
    }

    private void validateWeeklyScheduleResponse(final GetScheduleResponse response) {
        validateScheduleResponseBase(response);
        final CurrencySchedule schedule = response.getCurrency().get(Currency.USD);
        assertTrue(schedule.getRecurrence() instanceof ScheduleFrequencyWeeklyResponse);
        assertNotNull(((ScheduleFrequencyWeeklyResponse) schedule.getRecurrence()).getByDay());
    }

    private void validateDailyScheduleResponse(final GetScheduleResponse response) {
        validateScheduleResponseBase(response);
        final CurrencySchedule schedule = response.getCurrency().get(Currency.USD);
        assertTrue(schedule.getRecurrence() instanceof ScheduleFrequencyDailyResponse);
    }

    private void validateMonthlyScheduleResponse(final GetScheduleResponse response) {
        validateScheduleResponseBase(response);
        final CurrencySchedule schedule = response.getCurrency().get(Currency.USD);
        assertTrue(schedule.getRecurrence() instanceof ScheduleFrequencyMonthlyResponse);
        assertNotNull(((ScheduleFrequencyMonthlyResponse) schedule.getRecurrence()).getByMonthDay());
    }

    private void validateScheduleResponseBase(final GetScheduleResponse response) {
        assertNotNull(response);
        assertNotNull(response.getCurrency());
        assertFalse(response.getCurrency().isEmpty());
        assertNotNull(response.getLinks());
        assertFalse(response.getLinks().isEmpty());

        final CurrencySchedule schedule = response.getCurrency().get(Currency.USD);
        assertNotNull(schedule.getEnabled());
        assertNotNull(schedule.getThreshold());
        assertNotNull(schedule.getRecurrence());
        assertNotNull(schedule.getRecurrence().getFrequency());
    }

    private CheckoutApi getPayoutSchedulesCheckoutApi() {
        return CheckoutSdk.builder()
                .oAuth()
                .clientCredentials(
                        requireNonNull(System.getenv("CHECKOUT_DEFAULT_OAUTH_PAYOUT_SCHEDULE_CLIENT_ID")),
                        requireNonNull(System.getenv("CHECKOUT_DEFAULT_OAUTH_PAYOUT_SCHEDULE_CLIENT_SECRET")))
                .scopes(OAuthScope.MARKETPLACE)
                .environment(Environment.SANDBOX)
                .build();
    }

}