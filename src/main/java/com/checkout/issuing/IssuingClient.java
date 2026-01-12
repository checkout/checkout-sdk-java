package com.checkout.issuing;

import com.checkout.EmptyResponse;
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

public interface IssuingClient {

    CompletableFuture<CardholderResponse> createCardholder(CardholderRequest cardholderRequest);

    CompletableFuture<CardholderDetailsResponse> getCardholder(String cardholderId);

    CompletableFuture<CardholderCardsResponse> getCardholderCards(String cardholderId);

    CompletableFuture<CardResponse> createCard(CardRequest cardRequest);

    CompletableFuture<CardDetailsResponse> getCardDetails(String cardId);

    CompletableFuture<ThreeDSEnrollmentResponse> enrollThreeDS(String cardId, ThreeDSEnrollmentRequest enrollmentRequest);

    CompletableFuture<ThreeDSUpdateResponse> updateThreeDS(String cardId, ThreeDSUpdateRequest threeDSUpdateRequest);

    CompletableFuture<ThreeDSEnrollmentDetailsResponse> getCardThreeDSDetails(String cardId);

    CompletableFuture<VoidResponse> activateCard(String cardId);

    CompletableFuture<CardCredentialsResponse> getCardCredentials(final String cardId, final CardCredentialsQuery queryFilter);

    CompletableFuture<VoidResponse> revokeCard(final String cardId, final RevokeCardRequest revokeCardRequest);

    CompletableFuture<VoidResponse> suspendCard(final String cardId, final SuspendCardRequest suspendCardRequest);

    CompletableFuture<CardControlResponse> createControl(final CardControlRequest cardControlRequest);

    CompletableFuture<CardControlsQueryResponse> getCardControls(final CardControlsQuery queryFilter);

    CompletableFuture<CardControlResponse> getCardControlDetails(final String controlId);

    CompletableFuture<CardControlResponse> updateCardControl(final String controlId, final UpdateCardControlRequest updateCardControlRequest);

    CompletableFuture<IdResponse> removeCardControl(final String controlId);

    CompletableFuture<CardAuthorizationResponse> simulateAuthorization(final CardAuthorizationRequest cardAuthorizationRequest);

    CompletableFuture<CardAuthorizationIncrementingResponse> simulateIncrementingAuthorization(
            final String authorizationId,
            final CardAuthorizationIncrementingRequest cardAuthorizationIncrementingRequest
    );

    CompletableFuture<EmptyResponse> simulateClearing(
            final String authorizationId,
            final CardAuthorizationClearingRequest cardAuthorizationClearingRequest
    );

    CompletableFuture<CardAuthorizationReversalResponse> simulateReversal(
            final String authorizationId,
            final CardAuthorizationReversalRequest cardAuthorizationReversalRequest
    );

    // Synchronous methods
    CardholderResponse createCardholderSync(CardholderRequest cardholderRequest);

    CardholderDetailsResponse getCardholderSync(String cardholderId);

    CardholderCardsResponse getCardholderCardsSync(String cardholderId);

    CardResponse createCardSync(CardRequest cardRequest);

    CardDetailsResponse getCardDetailsSync(String cardId);

    ThreeDSEnrollmentResponse enrollThreeDSSync(String cardId, ThreeDSEnrollmentRequest enrollmentRequest);

    ThreeDSUpdateResponse updateThreeDSSync(String cardId, ThreeDSUpdateRequest threeDSUpdateRequest);

    ThreeDSEnrollmentDetailsResponse getCardThreeDSDetailsSync(String cardId);

    VoidResponse activateCardSync(String cardId);

    CardCredentialsResponse getCardCredentialsSync(String cardId, CardCredentialsQuery queryFilter);

    VoidResponse revokeCardSync(String cardId, RevokeCardRequest revokeCardRequest);

    VoidResponse suspendCardSync(String cardId, SuspendCardRequest suspendCardRequest);

    CardControlResponse createControlSync(CardControlRequest cardControlRequest);

    CardControlsQueryResponse getCardControlsSync(CardControlsQuery queryFilter);

    CardControlResponse getCardControlDetailsSync(String controlId);

    CardControlResponse updateCardControlSync(String controlId, UpdateCardControlRequest updateCardControlRequest);

    IdResponse removeCardControlSync(String controlId);

    CardAuthorizationResponse simulateAuthorizationSync(CardAuthorizationRequest cardAuthorizationRequest);

    CardAuthorizationIncrementingResponse simulateIncrementingAuthorizationSync(
            String authorizationId,
            CardAuthorizationIncrementingRequest cardAuthorizationIncrementingRequest
    );

    EmptyResponse simulateClearingSync(
            String authorizationId,
            CardAuthorizationClearingRequest cardAuthorizationClearingRequest
    );

    CardAuthorizationReversalResponse simulateReversalSync(
            String authorizationId,
            CardAuthorizationReversalRequest cardAuthorizationReversalRequest
    );
}