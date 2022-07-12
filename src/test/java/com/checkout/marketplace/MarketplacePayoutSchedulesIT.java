package com.checkout.marketplace;

import com.checkout.CheckoutSdk;
import com.checkout.Environment;
import com.checkout.FourOAuthScope;
import com.checkout.common.Currency;
import com.checkout.four.CheckoutApi;
import com.checkout.marketplace.payout.schedule.DaySchedule;
import com.checkout.marketplace.payout.schedule.request.ScheduleFrequencyDailyRequest;
import com.checkout.marketplace.payout.schedule.request.ScheduleFrequencyMonthlyRequest;
import com.checkout.marketplace.payout.schedule.request.ScheduleFrequencyWeeklyRequest;
import com.checkout.marketplace.payout.schedule.request.UpdateScheduleRequest;
import com.checkout.marketplace.payout.schedule.response.CurrencySchedule;
import com.checkout.marketplace.payout.schedule.response.GetScheduleResponseDeserializer;
import com.checkout.marketplace.payout.schedule.response.ScheduleFrequencyDailyResponse;
import com.checkout.marketplace.payout.schedule.response.ScheduleFrequencyMonthlyResponse;
import com.checkout.marketplace.payout.schedule.response.ScheduleFrequencyWeeklyResponse;
import com.checkout.marketplace.payout.schedule.response.VoidResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MarketplacePayoutSchedulesIT {

    @Test
    void shouldUpdateAndRetrieveWeeklyPayoutSchedules() throws ExecutionException, InterruptedException {

        final UpdateScheduleRequest updateScheduleRequest = UpdateScheduleRequest.builder()
                .enabled(true)
                .threshold(1000)
                .recurrence(ScheduleFrequencyWeeklyRequest.builder()
                        .byDay(DaySchedule.MONDAY)
                        .build())
                .build();

        final VoidResponse scheduleResponse = getPayoutSchedulesCheckoutApi().marketplaceClient()
                .updatePayoutSchedule("ent_sdioy6bajpzxyl3utftdp7legq", Currency.USD, updateScheduleRequest).get();

        assertNotNull(scheduleResponse);

        final GetScheduleResponseDeserializer getScheduleResponseDeserializer = getPayoutSchedulesCheckoutApi().marketplaceClient()
                .retrievePayoutSchedule("ent_sdioy6bajpzxyl3utftdp7legq").get();

        assertNotNull(getScheduleResponseDeserializer);
        assertNotNull(getScheduleResponseDeserializer.getCurrency());
        assertFalse(getScheduleResponseDeserializer.getCurrency().isEmpty());
        assertNotNull(getScheduleResponseDeserializer.getLinks());
        assertFalse(getScheduleResponseDeserializer.getLinks().isEmpty());

        final CurrencySchedule schedule = getScheduleResponseDeserializer.getCurrency().get(Currency.USD);
        assertNotNull(schedule.getEnabled());
        assertNotNull(schedule.getThreshold());
        assertNotNull(schedule.getRecurrence());
        assertTrue(schedule.getRecurrence() instanceof ScheduleFrequencyWeeklyResponse);
        assertNotNull(schedule.getRecurrence().getFrequency());
        assertNotNull(((ScheduleFrequencyWeeklyResponse) schedule.getRecurrence()).getByDay());
    }

    @Test
    void shouldUpdateAndRetrieveDailyPayoutSchedules() throws ExecutionException, InterruptedException {

        final UpdateScheduleRequest updateScheduleRequest = UpdateScheduleRequest.builder()
                .enabled(true)
                .threshold(1000)
                .recurrence(new ScheduleFrequencyDailyRequest())
                .build();

        final VoidResponse scheduleResponse = getPayoutSchedulesCheckoutApi().marketplaceClient()
                .updatePayoutSchedule("ent_sdioy6bajpzxyl3utftdp7legq", Currency.USD, updateScheduleRequest).get();

        assertNotNull(scheduleResponse);

        final GetScheduleResponseDeserializer getScheduleResponseDeserializer = getPayoutSchedulesCheckoutApi().marketplaceClient()
                .retrievePayoutSchedule("ent_sdioy6bajpzxyl3utftdp7legq").get();

        assertNotNull(getScheduleResponseDeserializer);
        assertNotNull(getScheduleResponseDeserializer.getCurrency());
        assertFalse(getScheduleResponseDeserializer.getCurrency().isEmpty());
        assertNotNull(getScheduleResponseDeserializer.getLinks());
        assertFalse(getScheduleResponseDeserializer.getLinks().isEmpty());

        final CurrencySchedule schedule = getScheduleResponseDeserializer.getCurrency().get(Currency.USD);
        assertNotNull(schedule.getEnabled());
        assertNotNull(schedule.getThreshold());
        assertNotNull(schedule.getRecurrence());
        assertTrue(schedule.getRecurrence() instanceof ScheduleFrequencyDailyResponse);
        assertNotNull(schedule.getRecurrence().getFrequency());
    }

    @Test
    void shouldUpdateAndRetrieveMonthlyPayoutSchedules() throws ExecutionException, InterruptedException {

        final UpdateScheduleRequest updateScheduleRequest = UpdateScheduleRequest.builder()
                .enabled(true)
                .threshold(1000)
                .recurrence(ScheduleFrequencyMonthlyRequest.builder()
                        .byMonthDay(15)
                        .build())
                .build();

        final VoidResponse scheduleResponse = getPayoutSchedulesCheckoutApi().marketplaceClient()
                .updatePayoutSchedule("ent_sdioy6bajpzxyl3utftdp7legq", Currency.USD, updateScheduleRequest).get();

        assertNotNull(scheduleResponse);

        final GetScheduleResponseDeserializer getScheduleResponseDeserializer = getPayoutSchedulesCheckoutApi().marketplaceClient()
                .retrievePayoutSchedule("ent_sdioy6bajpzxyl3utftdp7legq").get();

        assertNotNull(getScheduleResponseDeserializer);
        assertNotNull(getScheduleResponseDeserializer.getCurrency());
        assertFalse(getScheduleResponseDeserializer.getCurrency().isEmpty());
        assertNotNull(getScheduleResponseDeserializer.getLinks());
        assertFalse(getScheduleResponseDeserializer.getLinks().isEmpty());

        final CurrencySchedule schedule = getScheduleResponseDeserializer.getCurrency().get(Currency.USD);
        assertNotNull(schedule.getEnabled());
        assertNotNull(schedule.getThreshold());
        assertNotNull(schedule.getRecurrence());
        assertTrue(schedule.getRecurrence() instanceof ScheduleFrequencyMonthlyResponse);
        assertNotNull(schedule.getRecurrence().getFrequency());
        assertNotNull(((ScheduleFrequencyMonthlyResponse) schedule.getRecurrence()).getByMonthDay());
    }

    private CheckoutApi getPayoutSchedulesCheckoutApi() {
        return CheckoutSdk.fourSdk()
                .oAuth()
                .clientCredentials(
                        requireNonNull(System.getenv("CHECKOUT_FOUR_OAUTH_PAYOUT_SCHEDULE_CLIENT_ID")),
                        requireNonNull(System.getenv("CHECKOUT_FOUR_OAUTH_PAYOUT_SCHEDULE_CLIENT_SECRET")))
                .scopes(FourOAuthScope.MARKETPLACE)
                .environment(Environment.SANDBOX)
                .build();
    }

}