package com.checkout.marketplace;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.CheckoutException;
import com.checkout.CheckoutFilesApiConfiguration;
import com.checkout.FilesTransport;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class MarketplaceClientImpl extends AbstractClient implements MarketplaceClient {

    private static final String MARKETPLACE = "marketplace";
    private static final String ENTITIES = "entities";
    private static final String INSTRUMENTS = "instruments";
    private static final String FILES = "files";

    public MarketplaceClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.OAUTH);
    }

    @Override
    public CompletableFuture<OnboardEntityResponse> createEntity(final OnboardEntityRequest entityRequest) {
        validateParams("entityRequest", entityRequest);
        return apiClient.postAsync(buildPath(MARKETPLACE, ENTITIES), sdkAuthorization(), OnboardEntityResponse.class, entityRequest, null);
    }

    @Override
    public CompletableFuture<OnboardEntityDetailsResponse> getEntity(final String entityId) {
        validateParams("entityId", entityId);
        return apiClient.getAsync(buildPath(MARKETPLACE, ENTITIES, entityId), sdkAuthorization(), OnboardEntityDetailsResponse.class);
    }

    @Override
    public CompletableFuture<OnboardEntityResponse> updateEntity(final OnboardEntityRequest entityRequest, final String entityId) {
        validateParams("entityRequest", entityRequest, "entityId", entityId);
        return apiClient.putAsync(buildPath(MARKETPLACE, ENTITIES, entityId), sdkAuthorization(), OnboardEntityResponse.class, entityRequest);
    }

    @Override
    public CompletableFuture<Void> createPaymentInstrument(final MarketplacePaymentInstrument marketplacePaymentInstrument, final String entityId) {
        validateParams("marketplacePaymentInstrument", marketplacePaymentInstrument, "entityId", entityId);
        return apiClient.postAsync(buildPath(MARKETPLACE, ENTITIES, entityId, INSTRUMENTS), sdkAuthorization(), Void.class, marketplacePaymentInstrument, null);
    }

    @Override
    public CompletableFuture<IdResponse> submitFile(final MarketplaceFileRequest marketplaceFileRequest) {
        final CheckoutFilesApiConfiguration filesApiConfiguration = configuration.getFilesApiConfiguration();
        if (!filesApiConfiguration.isEnabled()) {
            throw new CheckoutException("Files API is not enabled in this client. It must be enabled in CheckoutFourSdk configuration.");
        }
        return apiClient.submitFileAsync(new FilesTransport(filesApiConfiguration.getEnvironment().getUri()), FILES, sdkAuthorization(), marketplaceFileRequest, IdResponse.class);
    }

}
