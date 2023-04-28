package com.checkout.issuing;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.cardholders.CardholderDetailsResponse;
import com.checkout.issuing.cardholders.CardholderRequest;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cards.requests.create.CardRequest;
import com.checkout.issuing.cards.requests.credentials.CardCredentialsQuery;
import com.checkout.issuing.cards.requests.enrollment.AbstractThreeDSEnrollmentRequest;
import com.checkout.issuing.cards.requests.enrollment.ThreeDSUpdateRequest;
import com.checkout.issuing.cards.requests.revoke.RevokeCardRequest;
import com.checkout.issuing.cards.requests.suspend.SuspendCardRequest;
import com.checkout.issuing.cards.responses.AbstractCardDetailsResponse;
import com.checkout.issuing.cards.responses.CardResponse;
import com.checkout.issuing.cards.responses.credentials.CardCredentialsResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSEnrollmentDetailsResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSEnrollmentResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSUpdateResponse;
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
    public CompletableFuture<AbstractCardDetailsResponse> getCardDetails(final String cardId) {
        validateParams("cardId", cardId);
        return apiClient.getAsync(
                buildPath(ISSUING_PATH, CARDS_PATH, cardId),
                sdkAuthorization(),
                AbstractCardDetailsResponse.class
        );
    }

    @Override
    public CompletableFuture<ThreeDSEnrollmentResponse> enrollThreeDS(
            final String cardId,
            final AbstractThreeDSEnrollmentRequest enrollmentRequest
    ) {
        validateParams("cardId", cardId,"enrollmentRequest", enrollmentRequest);
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
        validateParams("cardId", cardId,"queryFilter", queryFilter);
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
}
