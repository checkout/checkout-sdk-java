package com.checkout.forward;

import com.checkout.EmptyResponse;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.forward.requests.CreateSecretRequest;
import com.checkout.forward.requests.DestinationRequest;
import com.checkout.forward.requests.ForwardRequest;
import com.checkout.forward.requests.Headers;
import com.checkout.forward.requests.MethodType;
import com.checkout.forward.requests.NetworkToken;
import com.checkout.forward.requests.UpdateSecretRequest;
import com.checkout.forward.requests.signatures.DlocalParameters;
import com.checkout.forward.requests.signatures.DlocalSignature;
import com.checkout.forward.requests.sources.IdSource;
import com.checkout.forward.responses.ForwardAnApiResponse;
import com.checkout.forward.responses.GetForwardResponse;
import com.checkout.forward.responses.SecretResponse;
import com.checkout.forward.responses.SecretsListResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ForwardTestIT extends SandboxTestFixture {

    public ForwardTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldForwardAnApiRequest() {

        final ForwardRequest request = createForwardRequest();

        final ForwardAnApiResponse response = blocking(() -> checkoutApi.forwardClient().forwardAnApiRequest(request));

        validateForwardAnApiResponse(response);

    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldGetForwardRequest() {

        final ForwardRequest request = createForwardRequest();

        final ForwardAnApiResponse forwardResponse = blocking(() -> checkoutApi.forwardClient().forwardAnApiRequest(request));

        final GetForwardResponse response = blocking(() -> checkoutApi.forwardClient().getForwardRequest(forwardResponse.getRequestId()));

        validateGetForwardResponse(response);

    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldCreateSecret() {
        final CreateSecretRequest request = createSecretRequest();

        final SecretResponse response = blocking(() -> checkoutApi.forwardClient().createSecret(request));

        validateSecretResponse(response);
    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldListSecrets() {
        final SecretsListResponse response = blocking(() -> checkoutApi.forwardClient().listSecrets());

        validateSecretsListResponse(response);
    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldUpdateSecret() {
        final CreateSecretRequest createRequest = createSecretRequest();
        final SecretResponse createResponse = blocking(() -> checkoutApi.forwardClient().createSecret(createRequest));
        
        final UpdateSecretRequest updateRequest = createUpdateSecretRequest();
        final SecretResponse response = blocking(() -> checkoutApi.forwardClient().updateSecret(createResponse.getName(), updateRequest));

        validateSecretResponse(response);
    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldDeleteSecret() {
        final CreateSecretRequest createRequest = createSecretRequest();
        final SecretResponse createResponse = blocking(() -> checkoutApi.forwardClient().createSecret(createRequest));
        
        final EmptyResponse response = blocking(() -> checkoutApi.forwardClient().deleteSecret(createResponse.getName()));

        validateEmptyResponse(response);
    }

    // Sync methods
    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldForwardAnApiRequestSync() {

        final ForwardRequest request = createForwardRequest();

        final ForwardAnApiResponse response = checkoutApi.forwardClient().forwardAnApiRequestSync(request);

        validateForwardAnApiResponse(response);

    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldGetForwardRequestSync() {

        final ForwardRequest request = createForwardRequest();

        final ForwardAnApiResponse forwardResponse = checkoutApi.forwardClient().forwardAnApiRequestSync(request);
        final GetForwardResponse response = checkoutApi.forwardClient().getForwardRequestSync(forwardResponse.getRequestId());

        validateGetForwardResponse(response);

    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldCreateSecretSync() {
        final CreateSecretRequest request = createSecretRequest();

        final SecretResponse response = checkoutApi.forwardClient().createSecretSync(request);

        validateSecretResponse(response);
    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldListSecretsSync() {
        final SecretsListResponse response = checkoutApi.forwardClient().listSecretsSync();

        validateSecretsListResponse(response);
    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldUpdateSecretSync() {
        final CreateSecretRequest createRequest = createSecretRequest();
        final SecretResponse createResponse = checkoutApi.forwardClient().createSecretSync(createRequest);
        
        final UpdateSecretRequest updateRequest = createUpdateSecretRequest();
        final SecretResponse response = checkoutApi.forwardClient().updateSecretSync(createResponse.getName(), updateRequest);

        validateSecretResponse(response);
    }

    @Disabled("This test requires a valid id or Token source")
    @Test
    void shouldDeleteSecretSync() {
        final CreateSecretRequest createRequest = createSecretRequest();
        final SecretResponse createResponse = checkoutApi.forwardClient().createSecretSync(createRequest);
        
        final EmptyResponse response = checkoutApi.forwardClient().deleteSecretSync(createResponse.getName());

        validateEmptyResponse(response);
    }

    // Common methods
    private static ForwardRequest createForwardRequest() {
        final IdSource source = IdSource.builder()
                .id("src_v5rgkf3gdtpuzjqesyxmyodnya")
                .build();

        final NetworkToken networkToken = NetworkToken.builder()
                .enabled(true)
                .requestCryptogram(false)
                .build();

        final Map<String, String> raw = new HashMap<>();
        raw.put("Idempotency-Key", "xe4fad12367dfgrds");
        raw.put("Content-Type", "application/json");

        final Headers headers = Headers.builder()
                .raw(raw)
                .build();

        final DlocalParameters dlocalParameters = DlocalParameters.builder()
                .SecretKey("9f439fe1a9f96e67b047d3c1a28c33a2e")
                .build();

        final DlocalSignature dlocalSignature = DlocalSignature.builder()
                .dlocalParameters(dlocalParameters)
                .build();

        final DestinationRequest destinationRequest = DestinationRequest.builder()
                .method(MethodType.POST)
                .url("https://example.com/payments")
                .headers(headers)
                .body("{\"amount\": 1000, \"currency\": \"USD\", \"reference\": \"some_reference\", \"source\": {\"type\": \"card\", \"number\": \"{{card_number}}\", \"expiry_month\": \"{{card_expiry_month}}\", \"expiry_year\": \"{{card_expiry_year_yyyy}}\", \"name\": \"Ali Farid\"}, \"payment_type\": \"Regular\", \"authorization_type\": \"Final\", \"capture\": true, \"processing_channel_id\": \"pc_xxxxxxxxxxx\", \"risk\": {\"enabled\": false}, \"merchant_initiated\": true}")
                .signature(dlocalSignature)
                .build();

        return ForwardRequest.builder()
                .source(source)
                .reference("ORD-5023-4E89")
                .processingChannelId(requireNonNull(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID")))
                .networkToken(networkToken)
                .destinationRequest(destinationRequest)
                .build();
    }

    private CreateSecretRequest createSecretRequest() {
        return CreateSecretRequest.builder()
                .name("test_secret_" + System.currentTimeMillis())
                .value("test_secret_value")
                .entityId("ent_test_123")
                .build();
    }

    private UpdateSecretRequest createUpdateSecretRequest() {
        return UpdateSecretRequest.builder()
                .value("updated_secret_value")
                .entityId("ent_test_456")
                .build();
    }

    private void validateForwardAnApiResponse(final ForwardAnApiResponse response) {
        assertNotNull(response);
        assertNotNull(response.getRequestId());
        assertNotNull(response.getDestinationResponse());
    }

    private void validateGetForwardResponse(final GetForwardResponse response) {
        assertNotNull(response);
        assertNotNull(response.getRequestId());
        assertNotNull(response.getEntityId());
        assertNotNull(response.getDestinationRequest());
        assertNotNull(response.getCreatedOn());
        assertNotNull(response.getReference());
        assertNotNull(response.getDestinationResponse());
    }

    private void validateSecretResponse(final SecretResponse response) {
        assertNotNull(response);
        assertNotNull(response.getName());
        assertNotNull(response.getCreatedAt());
        assertNotNull(response.getUpdatedAt());
        assertNotNull(response.getVersion());
        assertTrue(response.getVersion() >= 1);
    }

    private void validateSecretsListResponse(final SecretsListResponse response) {
        assertNotNull(response);
        assertNotNull(response.getData());
    }

    private void validateEmptyResponse(final EmptyResponse response) {
        assertNotNull(response);
    }
}
