package com.checkout.networkTokens;

import com.checkout.EmptyResponse;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.networkTokens.entities.DeletionReason;
import com.checkout.networkTokens.entities.InitiatedBy;
import com.checkout.networkTokens.entities.TransactionType;
import com.checkout.networkTokens.requests.DeleteNetworkTokenRequest;
import com.checkout.networkTokens.requests.ProvisionNetworkTokenRequest;
import com.checkout.networkTokens.requests.RequestCryptogramRequest;
import com.checkout.networkTokens.requests.sources.CardNetworkTokenSource;
import com.checkout.networkTokens.requests.sources.IdNetworkTokenSource;
import com.checkout.networkTokens.responses.CryptogramResponse;
import com.checkout.networkTokens.responses.NetworkTokenResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Network token endpoints not yet available in test environment")
public class NetworkTokensTestIT extends SandboxTestFixture {

    public NetworkTokensTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    void shouldProvisionNetworkTokenWithId() {
        final ProvisionNetworkTokenRequest request = createProvisionNetworkTokenRequestWithId();

        final NetworkTokenResponse response = blocking(() -> checkoutApi.networkTokensClient().provisionNetworkToken(request));

        validateNetworkTokenResponse(response);
    }

    @Test
    void shouldProvisionNetworkTokenWithCard() {
        final ProvisionNetworkTokenRequest request = createProvisionNetworkTokenRequestWithCard();

        final NetworkTokenResponse response = blocking(() -> checkoutApi.networkTokensClient().provisionNetworkToken(request));

        validateNetworkTokenResponse(response);
    }

    @Test
    void shouldGetNetworkToken() {
        final ProvisionNetworkTokenRequest request = createProvisionNetworkTokenRequestWithCard();
        final NetworkTokenResponse provisionResponse = blocking(() -> checkoutApi.networkTokensClient().provisionNetworkToken(request));

        final NetworkTokenResponse response = blocking(() -> checkoutApi.networkTokensClient().getNetworkToken(provisionResponse.getNetworkToken().getId()));

        validateNetworkTokenResponse(response);
    }

    @Test
    void shouldRequestCryptogram() {
        final ProvisionNetworkTokenRequest request = createProvisionNetworkTokenRequestWithCard();
        final NetworkTokenResponse provisionResponse = blocking(() -> checkoutApi.networkTokensClient().provisionNetworkToken(request));

        final RequestCryptogramRequest cryptogramRequest = createRequestCryptogramRequest();
        final CryptogramResponse response = blocking(() -> checkoutApi.networkTokensClient().requestCryptogram(provisionResponse.getNetworkToken().getId(), cryptogramRequest));

        validateCryptogramResponse(response);
    }

    @Test
    void shouldDeleteNetworkToken() {
        final ProvisionNetworkTokenRequest request = createProvisionNetworkTokenRequestWithCard();
        final NetworkTokenResponse provisionResponse = blocking(() -> checkoutApi.networkTokensClient().provisionNetworkToken(request));

        final DeleteNetworkTokenRequest deleteRequest = createDeleteNetworkTokenRequest();
        final EmptyResponse response = blocking(() -> checkoutApi.networkTokensClient().deleteNetworkToken(provisionResponse.getNetworkToken().getId(), deleteRequest));

        validateEmptyResponse(response);
    }

    // Synchronous methods
    @Test
    void shouldProvisionNetworkTokenWithIdSync() {
        final ProvisionNetworkTokenRequest request = createProvisionNetworkTokenRequestWithId();

        final NetworkTokenResponse response = checkoutApi.networkTokensClient().provisionNetworkTokenSync(request);

        validateNetworkTokenResponse(response);
    }

    @Test
    void shouldProvisionNetworkTokenWithCardSync() {
        final ProvisionNetworkTokenRequest request = createProvisionNetworkTokenRequestWithCard();

        final NetworkTokenResponse response = checkoutApi.networkTokensClient().provisionNetworkTokenSync(request);

        validateNetworkTokenResponse(response);
    }

    @Test
    void shouldGetNetworkTokenSync() {
        final ProvisionNetworkTokenRequest request = createProvisionNetworkTokenRequestWithCard();
        final NetworkTokenResponse provisionResponse = checkoutApi.networkTokensClient().provisionNetworkTokenSync(request);

        final NetworkTokenResponse response = checkoutApi.networkTokensClient().getNetworkTokenSync(provisionResponse.getNetworkToken().getId());

        validateNetworkTokenResponse(response);
    }

    @Test
    void shouldRequestCryptogramSync() {
        final ProvisionNetworkTokenRequest request = createProvisionNetworkTokenRequestWithCard();
        final NetworkTokenResponse provisionResponse = checkoutApi.networkTokensClient().provisionNetworkTokenSync(request);

        final RequestCryptogramRequest cryptogramRequest = createRequestCryptogramRequest();
        final CryptogramResponse response = checkoutApi.networkTokensClient().requestCryptogramSync(provisionResponse.getNetworkToken().getId(), cryptogramRequest);

        validateCryptogramResponse(response);
    }

    @Test
    void shouldDeleteNetworkTokenSync() {
        final ProvisionNetworkTokenRequest request = createProvisionNetworkTokenRequestWithCard();
        final NetworkTokenResponse provisionResponse = checkoutApi.networkTokensClient().provisionNetworkTokenSync(request);

        final DeleteNetworkTokenRequest deleteRequest = createDeleteNetworkTokenRequest();
        final EmptyResponse response = checkoutApi.networkTokensClient().deleteNetworkTokenSync(provisionResponse.getNetworkToken().getId(), deleteRequest);

        validateEmptyResponse(response);
    }

    // Common methods
    private ProvisionNetworkTokenRequest createProvisionNetworkTokenRequestWithId() {
        final IdNetworkTokenSource source = IdNetworkTokenSource.builder()
                .id("src_wmlfc3zyhqzehihu7giusaaawu")
                .build();

        return ProvisionNetworkTokenRequest.builder()
                .source(source)
                .build();
    }

    private ProvisionNetworkTokenRequest createProvisionNetworkTokenRequestWithCard() {
        final CardNetworkTokenSource source = CardNetworkTokenSource.builder()
                .number("4539467987109256")
                .expiryMonth("10")
                .expiryYear("2027")
                .build();

        return ProvisionNetworkTokenRequest.builder()
                .source(source)
                .build();
    }

    private RequestCryptogramRequest createRequestCryptogramRequest() {
        return RequestCryptogramRequest.builder()
                .transactionType(TransactionType.ECOM)
                .build();
    }

    private DeleteNetworkTokenRequest createDeleteNetworkTokenRequest() {
        return DeleteNetworkTokenRequest.builder()
                .initiatedBy(InitiatedBy.TOKEN_REQUESTOR)
                .reason(DeletionReason.OTHER)
                .build();
    }

    private void validateNetworkTokenResponse(final NetworkTokenResponse response) {
        assertNotNull(response);
        assertNotNull(response.getCard());
        assertNotNull(response.getNetworkToken());
        assertNotNull(response.getNetworkToken().getId());
        assertNotNull(response.getNetworkToken().getState());
        assertNotNull(response.getNetworkToken().getType());
    }

    private void validateCryptogramResponse(final CryptogramResponse response) {
        assertNotNull(response);
        assertNotNull(response.getCryptogram());
    }

    private void validateEmptyResponse(final EmptyResponse response) {
        assertNotNull(response);
    }

}