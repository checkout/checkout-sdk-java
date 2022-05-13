package com.checkout.marketplace;

import com.checkout.EmptyResponse;
import com.checkout.common.Currency;
import com.checkout.common.IdResponse;
import com.checkout.marketplace.balances.BalancesQuery;
import com.checkout.marketplace.balances.BalancesResponse;
import com.checkout.marketplace.payout.schedule.request.UpdateScheduleRequest;
import com.checkout.marketplace.payout.schedule.response.GetScheduleResponse;
import com.checkout.marketplace.payout.schedule.response.VoidResponse;
import com.checkout.marketplace.transfers.CreateTransferRequest;
import com.checkout.marketplace.transfers.CreateTransferResponse;

import java.util.concurrent.CompletableFuture;

public interface MarketplaceClient {

    CompletableFuture<OnboardEntityResponse> createEntity(OnboardEntityRequest entityRequest);

    CompletableFuture<OnboardEntityDetailsResponse> getEntity(String entityId);

    CompletableFuture<OnboardEntityResponse> updateEntity(OnboardEntityRequest entityRequest, String entityId);

    CompletableFuture<EmptyResponse> createPaymentInstrument(MarketplacePaymentInstrument marketplacePaymentInstrument, String entityId);

    CompletableFuture<IdResponse> submitFile(MarketplaceFileRequest marketplaceFileRequest);

    CompletableFuture<CreateTransferResponse> initiateTransferOfFunds(CreateTransferRequest createTransferRequest);

    CompletableFuture<CreateTransferResponse> initiateTransferOfFunds(CreateTransferRequest createTransferRequest, String idempotencyKey);

    CompletableFuture<BalancesResponse> retrieveEntityBalances(String entityId, BalancesQuery balancesQuery);

    CompletableFuture<VoidResponse> updatePayoutSchedule(String entityId, Currency currency, UpdateScheduleRequest updateScheduleRequest);

    CompletableFuture<GetScheduleResponse> retrievePayoutSchedule(String entityId);

}
