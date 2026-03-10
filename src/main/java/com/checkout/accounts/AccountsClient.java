package com.checkout.accounts;

import java.util.concurrent.CompletableFuture;

import com.checkout.EmptyResponse;
import com.checkout.accounts.payout.schedule.request.UpdateScheduleRequest;
import com.checkout.accounts.payout.schedule.response.GetScheduleResponse;
import com.checkout.accounts.payout.schedule.response.VoidResponse;
import com.checkout.accounts.reserverules.responses.ReserveRuleCreateResponse;
import com.checkout.accounts.reserverules.responses.ReserveRuleRequest;
import com.checkout.accounts.reserverules.responses.ReserveRuleResponse;
import com.checkout.accounts.reserverules.responses.ReserveRulesResponse;
import com.checkout.accounts.files.request.FileUploadRequest;
import com.checkout.accounts.files.response.FileUploadResponse;
import com.checkout.accounts.files.response.FileDetailsResponse;
import com.checkout.common.Currency;
import com.checkout.common.IdResponse;

public interface AccountsClient {

    CompletableFuture<IdResponse> submitFile(AccountsFileRequest accountsFileRequest);

    CompletableFuture<FileUploadResponse> uploadFile(String entityId, FileUploadRequest fileUploadRequest);

    CompletableFuture<FileDetailsResponse> retrieveFile(String entityId, String fileId);

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

    CompletableFuture<EntityMembersResponse> getEntityMembers(String entityId);

    CompletableFuture<EntityMemberResponse> reinviteEntityMember(String entityId, String userId);

    CompletableFuture<ReserveRuleCreateResponse> createReserveRule(String entityId, ReserveRuleRequest reserveRuleRequest);

    CompletableFuture<ReserveRuleCreateResponse> updateReserveRule(String entityId, String reserveRuleId, 
                                                                    ReserveRuleRequest reserveRuleRequest);

    CompletableFuture<ReserveRuleResponse> getReserveRule(String entityId, String reserveRuleId);

    CompletableFuture<ReserveRulesResponse> getReserveRules(String entityId);

    // Synchronous methods
    IdResponse submitFileSync(final AccountsFileRequest accountsFileRequest);

    FileUploadResponse uploadFileSync(final String entityId, final FileUploadRequest fileUploadRequest);

    FileDetailsResponse retrieveFileSync(final String entityId, final String fileId);
        
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

    EntityMembersResponse getEntityMembersSync(final String entityId);

    EntityMemberResponse reinviteEntityMemberSync(final String entityId, final String userId);

    ReserveRuleCreateResponse createReserveRuleSync(final String entityId, final ReserveRuleRequest reserveRuleRequest);

    ReserveRuleCreateResponse updateReserveRuleSync(final String entityId, final String reserveRuleId, final ReserveRuleRequest reserveRuleRequest);

    ReserveRuleResponse getReserveRuleSync(final String entityId, final String reserveRuleId);

    ReserveRulesResponse getReserveRulesSync(final String entityId);

}
