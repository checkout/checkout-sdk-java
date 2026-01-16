package com.checkout.accounts;

import java.util.concurrent.CompletableFuture;

import com.checkout.EmptyResponse;
import com.checkout.accounts.payout.schedule.request.UpdateScheduleRequest;
import com.checkout.accounts.payout.schedule.response.GetScheduleResponse;
import com.checkout.accounts.payout.schedule.response.VoidResponse;
import com.checkout.common.Currency;
import com.checkout.common.IdResponse;

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

    CompletableFuture<IdResponse> updatePaymentInstrumentDetails(String entityId,
                                                                 String instrumentId,
                                                                 UpdatePaymentInstrumentRequest updatePaymentInstrumentRequest);

    CompletableFuture<PaymentInstrumentQueryResponse> queryPaymentInstruments(String entityId, PaymentInstrumentsQuery query);

    CompletableFuture<GetScheduleResponse> retrievePayoutSchedule(String entityId);

    CompletableFuture<VoidResponse> updatePayoutSchedule(String entityId, Currency currency, UpdateScheduleRequest updateScheduleRequest);

    // Synchronous methods
    IdResponse submitFileSync(final AccountsFileRequest accountsFileRequest);
        
    OnboardEntityResponse createEntitySync(final OnboardEntityRequest entityRequest);

    PaymentInstrumentDetailsResponse retrievePaymentInstrumentDetailsSync(final String entityId, final String paymentInstrumentId);

    OnboardEntityDetailsResponse getEntitySync(final String entityId);

    OnboardEntityResponse updateEntitySync(final OnboardEntityRequest entityRequest, final String entityId);

    EmptyResponse createPaymentInstrumentSync(final AccountsPaymentInstrument accountsPaymentInstrument, final String entityId);

    IdResponse createPaymentInstrumentSync(final String entityId, final PaymentInstrumentRequest paymentInstrumentRequest);

    IdResponse updatePaymentInstrumentDetailsSync(final String entityId,
                                                    final String instrumentId,
                                                    final UpdatePaymentInstrumentRequest updatePaymentInstrumentRequest);
    PaymentInstrumentQueryResponse queryPaymentInstrumentsSync(final String entityId, final PaymentInstrumentsQuery query);

    GetScheduleResponse retrievePayoutScheduleSync(final String entityId);

    VoidResponse updatePayoutScheduleSync(final String entityId, final Currency currency, final UpdateScheduleRequest updateScheduleRequest);
}
