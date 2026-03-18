package com.checkout.forward;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.CheckoutUtils;
import com.checkout.forward.requests.CreateSecretRequest;
import com.checkout.forward.requests.ForwardRequest;
import com.checkout.forward.requests.UpdateSecretRequest;
import com.checkout.forward.responses.ForwardAnApiResponse;
import com.checkout.forward.responses.GetForwardResponse;
import com.checkout.forward.responses.SecretResponse;
import com.checkout.forward.responses.SecretsListResponse;

import java.util.concurrent.CompletableFuture;

public class ForwardClientImpl extends AbstractClient implements ForwardClient {

    private static final String FORWARD_PATH = "forward";
    private static final String SECRETS_PATH = "secrets";

    public ForwardClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<ForwardAnApiResponse> forwardAnApiRequest(final ForwardRequest forwardRequest) {
        CheckoutUtils.validateParams("forwardRequest", forwardRequest);
        return apiClient.postAsync(FORWARD_PATH, sdkAuthorization(), ForwardAnApiResponse.class, forwardRequest, null);
    }

    @Override
    public CompletableFuture<GetForwardResponse> getForwardRequest(final String forwardId) {
        CheckoutUtils.validateParams("forwardId", forwardId);
        return apiClient.getAsync(buildPath(FORWARD_PATH, forwardId), sdkAuthorization(), GetForwardResponse.class);
    }

    @Override
    public CompletableFuture<SecretResponse> createSecret(final CreateSecretRequest createSecretRequest) {
        CheckoutUtils.validateParams("createSecretRequest", createSecretRequest);
        return apiClient.postAsync(buildPath(FORWARD_PATH, SECRETS_PATH), sdkAuthorization(), SecretResponse.class, createSecretRequest, null);
    }

    @Override
    public CompletableFuture<SecretsListResponse> listSecrets() {
        return apiClient.getAsync(buildPath(FORWARD_PATH, SECRETS_PATH), sdkAuthorization(), SecretsListResponse.class);
    }

    @Override
    public CompletableFuture<SecretResponse> updateSecret(final String name, final UpdateSecretRequest updateSecretRequest) {
        CheckoutUtils.validateParams("name", name,"updateSecretRequest", updateSecretRequest);
        return apiClient.patchAsync(buildPath(FORWARD_PATH, SECRETS_PATH, name), sdkAuthorization(), SecretResponse.class, updateSecretRequest, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> deleteSecret(final String name) {
        CheckoutUtils.validateParams("name", name);
        return apiClient.deleteAsync(buildPath(FORWARD_PATH, SECRETS_PATH, name), sdkAuthorization());
    }

    // Synchronous methods
    @Override
    public ForwardAnApiResponse forwardAnApiRequestSync(final ForwardRequest forwardRequest) {
        CheckoutUtils.validateParams("forwardRequest", forwardRequest);
        return apiClient.post(FORWARD_PATH, sdkAuthorization(), ForwardAnApiResponse.class, forwardRequest, null);
    }

    @Override
    public GetForwardResponse getForwardRequestSync(final String forwardId) {
        CheckoutUtils.validateParams("forwardId", forwardId);
        return apiClient.get(buildPath(FORWARD_PATH, forwardId), sdkAuthorization(), GetForwardResponse.class);
    }

    @Override
    public SecretResponse createSecretSync(final CreateSecretRequest createSecretRequest) {
        CheckoutUtils.validateParams("createSecretRequest", createSecretRequest);
        return apiClient.post(buildPath(FORWARD_PATH, SECRETS_PATH), sdkAuthorization(), SecretResponse.class, createSecretRequest, null);
    }

    @Override
    public SecretsListResponse listSecretsSync() {
        return apiClient.get(buildPath(FORWARD_PATH, SECRETS_PATH), sdkAuthorization(), SecretsListResponse.class);
    }

    @Override
    public SecretResponse updateSecretSync(final String name, final UpdateSecretRequest updateSecretRequest) {
        CheckoutUtils.validateParams("name", name, "updateSecretRequest", updateSecretRequest);
        return apiClient.patch(buildPath(FORWARD_PATH, SECRETS_PATH, name), sdkAuthorization(), SecretResponse.class, updateSecretRequest, null);
    }

    @Override
    public EmptyResponse deleteSecretSync(final String name) {
        CheckoutUtils.validateParams("name", name);
        return apiClient.delete(buildPath(FORWARD_PATH, SECRETS_PATH, name), sdkAuthorization());
    }

}
