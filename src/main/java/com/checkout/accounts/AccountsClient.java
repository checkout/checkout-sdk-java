package com.checkout.accounts;

import com.checkout.EmptyResponse;
import com.checkout.accounts.payout.schedule.request.UpdateScheduleRequest;
import com.checkout.accounts.payout.schedule.response.GetScheduleResponse;
import com.checkout.accounts.payout.schedule.response.VoidResponse;
import com.checkout.common.Currency;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

public interface AccountsClient {

    CompletableFuture<IdResponse> submitFile(AccountsFileRequest accountsFileRequest);

    CompletableFuture<OnboardEntityResponse> createEntity(OnboardEntityRequest entityRequest);

    CompletableFuture<PaymentInstrumentDetailsResponse> retrievePaymentInstrumentDetails(String entityId, String paymentInstrumentId);

    CompletableFuture<OnboardEntityDetailsResponse> getEntity(String entityId);

    CompletableFuture<OnboardEntityResponse> updateEntity(OnboardEntityRequest entityRequest, String entityId);

    /**
     * @deprecated Use {{@link #createPaymentInstrument(String, PaymentInstrumentRequest)}} instead
     */
    @Deprecated
    CompletableFuture<EmptyResponse> createPaymentInstrument(AccountsPaymentInstrument accountsPaymentInstrument, String entityId);

    CompletableFuture<IdResponse> createPaymentInstrument(String entityId, PaymentInstrumentRequest paymentInstrumentRequest);

    CompletableFuture<PaymentInstrumentQueryResponse> queryPaymentInstruments(String entityId, PaymentInstrumentsQuery query);

    CompletableFuture<GetScheduleResponse> retrievePayoutSchedule(String entityId);

    CompletableFuture<VoidResponse> updatePayoutSchedule(String entityId, Currency currency, UpdateScheduleRequest updateScheduleRequest);

}
