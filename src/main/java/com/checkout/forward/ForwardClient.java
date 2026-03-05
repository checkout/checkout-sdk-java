package com.checkout.forward;

import com.checkout.EmptyResponse;
import com.checkout.forward.requests.CreateSecretRequest;
import com.checkout.forward.requests.ForwardRequest;
import com.checkout.forward.requests.UpdateSecretRequest;
import com.checkout.forward.responses.ForwardAnApiResponse;
import com.checkout.forward.responses.GetForwardResponse;
import com.checkout.forward.responses.SecretResponse;
import com.checkout.forward.responses.SecretsListResponse;

import java.util.concurrent.CompletableFuture;

public interface ForwardClient {

    /**
     * Forward an API request
     * <b>Beta</b>
     * Forwards an API request to a third-party endpoint.
     * For example, you can forward payment credentials you've stored in our Vault to a third-party payment processor.
     */
    CompletableFuture<ForwardAnApiResponse> forwardAnApiRequest(ForwardRequest forwardRequest);

    /**
     * Get forward request
     * Retrieve the details of a successfully forwarded API request.
     * The details can be retrieved for up to 14 days after the request was initiated.
     */
    CompletableFuture<GetForwardResponse> getForwardRequest(String forwardId);

    /**
     * Create secret
     * Create a new secret with a plaintext value.
     * Validation Rules:
     * - name: 1-64 characters, alphanumeric + underscore
     * - value: max 8KB
     * - entity_id (optional): when provided, secret is scoped to this entity
     */
    CompletableFuture<SecretResponse> createSecret(CreateSecretRequest createSecretRequest);

    /**
     * List secrets
     * Returns metadata for secrets scoped for client_id.
     */
    CompletableFuture<SecretsListResponse> listSecrets();

    /**
     * Update secret
     * Update an existing secret. After updating, the version is automatically incremented.
     * Validation Rules:
     * - Only value and entity_id can be updated
     * - value: max 8KB
     */
    CompletableFuture<SecretResponse> updateSecret(String name, UpdateSecretRequest updateSecretRequest);

    /**
     * Delete secret
     * Permanently delete a secret by name.
     */
    CompletableFuture<EmptyResponse> deleteSecret(String name);

    // Synchronous methods
    ForwardAnApiResponse forwardAnApiRequestSync(ForwardRequest forwardRequest);
    
    GetForwardResponse getForwardRequestSync(String forwardId);

    SecretResponse createSecretSync(CreateSecretRequest createSecretRequest);

    SecretsListResponse listSecretsSync();

    SecretResponse updateSecretSync(String name, UpdateSecretRequest updateSecretRequest);

    EmptyResponse deleteSecretSync(String name);

}
