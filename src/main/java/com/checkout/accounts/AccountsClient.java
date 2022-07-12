package com.checkout.accounts;

import com.checkout.EmptyResponse;
import com.checkout.common.Currency;
import com.checkout.common.IdResponse;
import com.checkout.accounts.payout.schedule.request.UpdateScheduleRequest;
import com.checkout.accounts.payout.schedule.response.GetScheduleResponse;
import com.checkout.accounts.payout.schedule.response.VoidResponse;

import java.util.concurrent.CompletableFuture;

public interface AccountsClient {

    CompletableFuture<OnboardEntityResponse> createEntity(OnboardEntityRequest entityRequest);

    CompletableFuture<OnboardEntityDetailsResponse> getEntity(String entityId);

    CompletableFuture<OnboardEntityResponse> updateEntity(OnboardEntityRequest entityRequest, String entityId);

    CompletableFuture<EmptyResponse> createPaymentInstrument(AccountsPaymentInstrument accountsPaymentInstrument, String entityId);

    CompletableFuture<IdResponse> submitFile(AccountsFileRequest accountsFileRequest);

    CompletableFuture<VoidResponse> updatePayoutSchedule(String entityId, Currency currency, UpdateScheduleRequest updateScheduleRequest);

    CompletableFuture<GetScheduleResponse> retrievePayoutSchedule(String entityId);

}
