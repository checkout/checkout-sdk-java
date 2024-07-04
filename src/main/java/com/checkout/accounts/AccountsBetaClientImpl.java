package com.checkout.accounts;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class AccountsBetaClientImpl extends AccountsClientImpl {

    private static final String ACCOUNTS_PATH_BETA = "accounts-beta";

    public AccountsBetaClientImpl(final ApiClient apiClient,
                                  final ApiClient filesClient,
                                  final CheckoutConfiguration configuration) {
        super(apiClient, filesClient, configuration);
    }

    @Override
    public CompletableFuture<OnboardEntityResponse> createEntity(final OnboardEntityRequest entityRequest) {
        validateParams("entityRequest", entityRequest);
        return apiClient.postAsync(
                buildPath(ACCOUNTS_PATH_BETA, ENTITIES_PATH),
                sdkAuthorization(),
                OnboardEntityResponse.class,
                entityRequest,
                null);
    }

    @Override
    public CompletableFuture<OnboardEntityDetailsResponse> getEntity(final String entityId) {
        validateParams("entityId", entityId);
        return apiClient.getAsync(
                buildPath(ACCOUNTS_PATH_BETA, ENTITIES_PATH, entityId),
                sdkAuthorization(),
                OnboardEntityDetailsResponse.class);
    }

    @Override
    public CompletableFuture<OnboardEntityResponse> updateEntity(final OnboardEntityRequest entityRequest, final String entityId) {
        validateParams("entityRequest", entityRequest, "entityId", entityId);
        return apiClient.putAsync(
                buildPath(ACCOUNTS_PATH_BETA, ENTITIES_PATH, entityId),
                sdkAuthorization(),
                OnboardEntityResponse.class,
                entityRequest);
    }

}
