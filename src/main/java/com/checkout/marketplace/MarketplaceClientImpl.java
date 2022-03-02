package com.checkout.marketplace;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.CheckoutException;
import com.checkout.FilesTransport;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class MarketplaceClientImpl extends AbstractClient implements MarketplaceClient {

    private static final String MARKETPLACE_PATH = "marketplace";
    private static final String ENTITIES_PATH = "entities";
    private static final String INSTRUMENTS_PATH = "instruments";
    private static final String FILES_PATH = "files";
    private static final String TRANSFERS_PATH = "transfers";

    public MarketplaceClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.OAUTH);
    }

    @Override
    public CompletableFuture<OnboardEntityResponse> createEntity(final OnboardEntityRequest entityRequest) {
        validateParams("entityRequest", entityRequest);
        return apiClient.postAsync(buildPath(MARKETPLACE_PATH, ENTITIES_PATH), sdkAuthorization(), OnboardEntityResponse.class, entityRequest, null);
    }

    @Override
    public CompletableFuture<OnboardEntityDetailsResponse> getEntity(final String entityId) {
        validateParams("entityId", entityId);
        return apiClient.getAsync(buildPath(MARKETPLACE_PATH, ENTITIES_PATH, entityId), sdkAuthorization(), OnboardEntityDetailsResponse.class);
    }

    @Override
    public CompletableFuture<OnboardEntityResponse> updateEntity(final OnboardEntityRequest entityRequest, final String entityId) {
        validateParams("entityRequest", entityRequest, "entityId", entityId);
        return apiClient.putAsync(buildPath(MARKETPLACE_PATH, ENTITIES_PATH, entityId), sdkAuthorization(), OnboardEntityResponse.class, entityRequest);
    }

    @Override
    public CompletableFuture<Void> createPaymentInstrument(final MarketplacePaymentInstrument marketplacePaymentInstrument, final String entityId) {
        validateParams("marketplacePaymentInstrument", marketplacePaymentInstrument, "entityId", entityId);
        return apiClient.postAsync(buildPath(MARKETPLACE_PATH, ENTITIES_PATH, entityId, INSTRUMENTS_PATH), sdkAuthorization(), Void.class, marketplacePaymentInstrument, null);
    }

    @Override
    public CompletableFuture<IdResponse> submitFile(final MarketplaceFileRequest marketplaceFileRequest) {
        if (configuration.getFilesApiConfiguration() == null) {
            throw new CheckoutException("Files API is not enabled. It must be initialized in the SDK.");
        }
        return apiClient.submitFileAsync(new FilesTransport(configuration), FILES_PATH, sdkAuthorization(), marketplaceFileRequest, IdResponse.class);
    }

    @Override
    public CompletableFuture<CreateTransferResponse> initiateTransferOfFunds(final CreateTransferRequest createTransferRequest) {
        validateParams("createTransferRequest", createTransferRequest);
        return apiClient.postAsync(TRANSFERS_PATH, sdkAuthorization(), CreateTransferResponse.class, createTransferRequest, null);
    }
}
