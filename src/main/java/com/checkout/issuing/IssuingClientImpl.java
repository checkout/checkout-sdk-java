package com.checkout.issuing;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.PlatformType;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.cardissuing.cardholderaccesstokens.requests.RequestAnAccessTokenRequest;
import com.checkout.cardissuing.cardholderaccesstokens.responses.RequestAnAccessTokenResponse;
import com.checkout.common.IdResponse;
import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.cardholders.CardholderDetailsResponse;
import com.checkout.issuing.cardholders.CardholderRequest;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cards.requests.create.CardRequest;
import com.checkout.issuing.cards.requests.credentials.CardCredentialsQuery;
import com.checkout.issuing.cards.requests.enrollment.ThreeDSEnrollmentRequest;
import com.checkout.issuing.cards.requests.enrollment.ThreeDSUpdateRequest;
import com.checkout.issuing.cards.requests.revoke.RevokeCardRequest;
import com.checkout.issuing.cards.requests.suspend.SuspendCardRequest;
import com.checkout.issuing.cards.responses.CardDetailsResponse;
import com.checkout.issuing.cards.responses.CardResponse;
import com.checkout.issuing.cards.responses.credentials.CardCredentialsResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSEnrollmentDetailsResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSEnrollmentResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSUpdateResponse;
import com.checkout.issuing.controls.requests.create.CardControlRequest;
import com.checkout.issuing.controls.requests.query.CardControlsQuery;
import com.checkout.issuing.controls.requests.update.UpdateCardControlRequest;
import com.checkout.issuing.controls.responses.create.CardControlResponse;
import com.checkout.issuing.controls.responses.query.CardControlsQueryResponse;
import com.checkout.issuing.testing.requests.CardAuthorizationClearingRequest;
import com.checkout.issuing.testing.requests.CardAuthorizationIncrementingRequest;
import com.checkout.issuing.testing.requests.CardAuthorizationRequest;
import com.checkout.issuing.testing.requests.CardAuthorizationReversalRequest;
import com.checkout.issuing.testing.responses.CardAuthorizationIncrementingResponse;
import com.checkout.issuing.testing.responses.CardAuthorizationResponse;
import com.checkout.issuing.testing.responses.CardAuthorizationReversalResponse;
import com.checkout.payments.VoidResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class IssuingClientImpl extends AbstractClient implements IssuingClient {

    private static final String ISSUING_PATH = "issuing";

    private static final String CARDHOLDERS_PATH = "cardholders";

    private static final String CARDS_PATH = "cards";

    private static final String THREE_DS_ENROLLMENT_PATH = "3ds-enrollment";

    private static final String ACTIVATE_PATH = "activate";

    private static final String CREDENTIALS_PATH = "credentials";

    private static final String REVOKE_PATH = "revoke";

    private static final String SUSPEND_PATH = "suspend";

    private static final String CONTROLS_PATH = "controls";

    private static final String ACCESS_PATH = "access";

    private static final String CONNECT_PATH = "connect";

    private static final String TOKEN_PATH = "token";

    private static final String SIMULATE_PATH = "simulate";

    private static final String AUTHORIZATIONS_PATH = "authorizations";

    private static final String PRESENTMENTS_PATH = "presentments";

    private static final String REVERSALS_PATH = "reversals";

    public IssuingClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<CardholderResponse> createCardholder(final CardholderRequest cardholderRequest) {
        validateParams("cardholderRequest", cardholderRequest);
        return apiClient.postAsync(
                buildPath(ISSUING_PATH, CARDHOLDERS_PATH),
                sdkAuthorization(),
                CardholderResponse.class,
                cardholderRequest,
                null
        );
    }

    @Override
    public CompletableFuture<CardholderDetailsResponse> getCardholder(final String cardholderId) {
        validateParams("cardholderId", cardholderId);
        return apiClient.getAsync(
                buildPath(ISSUING_PATH, CARDHOLDERS_PATH, cardholderId),
                sdkAuthorization(),
                CardholderDetailsResponse.class
        );
    }

    @Override
    public CompletableFuture<CardholderCardsResponse> getCardholderCards(final String cardholderId) {
        validateParams("cardholderId", cardholderId);
        return apiClient.getAsync(
                buildPath(ISSUING_PATH, CARDHOLDERS_PATH, cardholderId, CARDS_PATH),
                sdkAuthorization(),
                CardholderCardsResponse.class
        );
    }

    @Override
    public CompletableFuture<CardResponse> createCard(final CardRequest cardRequest) {
        validateParams("cardRequest", cardRequest);
        return apiClient.postAsync(
                buildPath(ISSUING_PATH, CARDS_PATH),
                sdkAuthorization(),
                CardResponse.class,
                cardRequest,
                null
        );
    }

    @Override
    public CompletableFuture<CardDetailsResponse> getCardDetails(final String cardId) {
        validateParams("cardId", cardId);
        return apiClient.getAsync(
                buildPath(ISSUING_PATH, CARDS_PATH, cardId),
                sdkAuthorization(),
                CardDetailsResponse.class
        );
    }

    @Override
    public CompletableFuture<ThreeDSEnrollmentResponse> enrollThreeDS(
            final String cardId,
            final ThreeDSEnrollmentRequest enrollmentRequest
    ) {
        validateParams("cardId", cardId, "enrollmentRequest", enrollmentRequest);
        return apiClient.postAsync(
                buildPath(ISSUING_PATH, CARDS_PATH, cardId, THREE_DS_ENROLLMENT_PATH),
                sdkAuthorization(),
                ThreeDSEnrollmentResponse.class,
                enrollmentRequest,
                null
        );
    }

    @Override
    public CompletableFuture<ThreeDSUpdateResponse> updateThreeDS(
            final String cardId,
            final ThreeDSUpdateRequest threeDSUpdateRequest
    ) {
        validateParams("cardId", cardId, "threeDSUpdateRequest", threeDSUpdateRequest);
        return apiClient.patchAsync(
                buildPath(ISSUING_PATH, CARDS_PATH, cardId, THREE_DS_ENROLLMENT_PATH),
                sdkAuthorization(),
                ThreeDSUpdateResponse.class,
                threeDSUpdateRequest,
                null
        );
    }

    @Override
    public CompletableFuture<ThreeDSEnrollmentDetailsResponse> getCardThreeDSDetails(final String cardId) {
        validateParams("cardId", cardId);
        return apiClient.getAsync(
                buildPath(ISSUING_PATH, CARDS_PATH, cardId, THREE_DS_ENROLLMENT_PATH),
                sdkAuthorization(),
                ThreeDSEnrollmentDetailsResponse.class
        );
    }

    @Override
    public CompletableFuture<VoidResponse> activateCard(final String cardId) {
        validateParams("cardId", cardId);
        return apiClient.postAsync(
                buildPath(ISSUING_PATH, CARDS_PATH, cardId, ACTIVATE_PATH),
                sdkAuthorization(),
                VoidResponse.class,
                null,
                null
        );
    }

    @Override
    public CompletableFuture<CardCredentialsResponse> getCardCredentials(
            final String cardId,
            final CardCredentialsQuery queryFilter
    ) {
        validateParams("cardId", cardId, "queryFilter", queryFilter);
        return apiClient.queryAsync(
                buildPath(ISSUING_PATH, CARDS_PATH, cardId, CREDENTIALS_PATH),
                sdkAuthorization(),
                queryFilter,
                CardCredentialsResponse.class);
    }

    @Override
    public CompletableFuture<VoidResponse> revokeCard(final String cardId, final RevokeCardRequest revokeCardRequest) {
        validateParams("cardId", cardId, "revokeCardRequest", revokeCardRequest);
        return apiClient.postAsync(
                buildPath(ISSUING_PATH, CARDS_PATH, cardId, REVOKE_PATH),
                sdkAuthorization(),
                VoidResponse.class,
                revokeCardRequest,
                null
        );
    }

    @Override
    public CompletableFuture<VoidResponse> suspendCard(
            final String cardId,
            final SuspendCardRequest suspendCardRequest
    ) {
        validateParams("cardId", cardId, "suspendCardRequest", suspendCardRequest);
        return apiClient.postAsync(
                buildPath(ISSUING_PATH, CARDS_PATH, cardId, SUSPEND_PATH),
                sdkAuthorization(),
                VoidResponse.class,
                suspendCardRequest,
                null
        );
    }

    @Override
    public CompletableFuture<CardControlResponse> createControl(
            final CardControlRequest cardControlRequest
    ) {
        validateParams("cardControlRequest", cardControlRequest);
        return apiClient.postAsync(
                buildPath(ISSUING_PATH, CONTROLS_PATH),
                sdkAuthorization(),
                CardControlResponse.class,
                cardControlRequest,
                null
        );
    }

    @Override
    public CompletableFuture<CardControlsQueryResponse> getCardControls(
            final CardControlsQuery queryFilter
    ) {
        validateParams("queryFilter", queryFilter);
        return apiClient.queryAsync(
                buildPath(ISSUING_PATH, CONTROLS_PATH),
                sdkAuthorization(),
                queryFilter,
                CardControlsQueryResponse.class);
    }

    @Override
    public CompletableFuture<CardControlResponse> getCardControlDetails(final String controlId) {
        validateParams("controlId", controlId);
        return apiClient.getAsync(
                buildPath(ISSUING_PATH, CONTROLS_PATH, controlId),
                sdkAuthorization(),
                CardControlResponse.class
        );
    }

    @Override
    public CompletableFuture<CardControlResponse> updateCardControl(
            final String controlId,
            final UpdateCardControlRequest updateCardControlRequest
    ) {
        validateParams("controlId", controlId, "updateCardControlRequest", updateCardControlRequest);
        return apiClient.putAsync(
                buildPath(ISSUING_PATH, CONTROLS_PATH, controlId),
                sdkAuthorization(),
                CardControlResponse.class,
                updateCardControlRequest
        );
    }

    @Override
    public CompletableFuture<IdResponse> removeCardControl(final String controlId) {
        validateParams("controlId", controlId);
        return apiClient.deleteAsync(
                buildPath(ISSUING_PATH, CONTROLS_PATH, controlId),
                sdkAuthorization(),
                IdResponse.class
        );
    }

    @Override
    public CompletableFuture<RequestAnAccessTokenResponse> RequestAnAccessToken (
        final RequestAnAccessTokenRequest requestAnAccessTokenRequest
    ) {
        validateParams("requestAnAccessTokenRequest", requestAnAccessTokenRequest);
        return apiClient.postFormUrlEncodedAsync(
                buildPath(ISSUING_PATH, ACCESS_PATH, CONNECT_PATH, TOKEN_PATH),
                new SdkAuthorization(PlatformType.DEFAULT, ""),
                requestAnAccessTokenRequest.toFormParams(),
                RequestAnAccessTokenResponse.class
        );
    }

    @Override
    public CompletableFuture<CardAuthorizationResponse> simulateAuthorization(final CardAuthorizationRequest cardAuthorizationRequest) {
        validateParams("cardAuthorizationRequest", cardAuthorizationRequest);
        return apiClient.postAsync(
                buildPath(ISSUING_PATH, SIMULATE_PATH, AUTHORIZATIONS_PATH),
                sdkAuthorization(),
                CardAuthorizationResponse.class,
                cardAuthorizationRequest,
                null
        );
    }

    @Override
    public CompletableFuture<CardAuthorizationIncrementingResponse> simulateIncrementingAuthorization(
            String authorizationId,
            CardAuthorizationIncrementingRequest cardAuthorizationIncrementingRequest
    ) {
        validateParams("authorizationId", authorizationId, "cardAuthorizationIncrementingRequest", cardAuthorizationIncrementingRequest);
        return apiClient.postAsync(
                buildPath(ISSUING_PATH, SIMULATE_PATH, AUTHORIZATIONS_PATH, authorizationId, AUTHORIZATIONS_PATH),
                sdkAuthorization(),
                CardAuthorizationIncrementingResponse.class,
                cardAuthorizationIncrementingRequest,
                null
        );
    }

    @Override
    public CompletableFuture<EmptyResponse> simulateClearing(
            String authorizationId,
            CardAuthorizationClearingRequest cardAuthorizationClearingRequest
    ) {
        validateParams("authorizationId", authorizationId, "cardAuthorizationClearingRequest", cardAuthorizationClearingRequest);
        return apiClient.postAsync(
                buildPath(ISSUING_PATH, SIMULATE_PATH, AUTHORIZATIONS_PATH, authorizationId, PRESENTMENTS_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                cardAuthorizationClearingRequest,
                null
        );
    }

    @Override
    public CompletableFuture<CardAuthorizationReversalResponse> simulateReversal(
            String authorizationId,
            CardAuthorizationReversalRequest cardAuthorizationReversalRequest
    ) {
        validateParams("authorizationId", authorizationId, "cardAuthorizationReversalRequest", cardAuthorizationReversalRequest);
        return apiClient.postAsync(
                buildPath(ISSUING_PATH, SIMULATE_PATH, AUTHORIZATIONS_PATH, authorizationId, REVERSALS_PATH),
                sdkAuthorization(),
                CardAuthorizationReversalResponse.class,
                cardAuthorizationReversalRequest,
                null
        );
    }
}
