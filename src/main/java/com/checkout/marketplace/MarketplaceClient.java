package com.checkout.marketplace;

import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

public interface MarketplaceClient {

    CompletableFuture<OnboardEntityResponse> createEntity(OnboardEntityRequest entityRequest);

    CompletableFuture<OnboardEntityDetailsResponse> getEntity(String entityId);

    CompletableFuture<OnboardEntityResponse> updateEntity(OnboardEntityRequest entityRequest, String entityId);

    CompletableFuture<Void> createPaymentInstrument(MarketplacePaymentInstrument marketplacePaymentInstrument, String entityId);

    CompletableFuture<IdResponse> submitFile(MarketplaceFileRequest marketplaceFileRequest);

}
