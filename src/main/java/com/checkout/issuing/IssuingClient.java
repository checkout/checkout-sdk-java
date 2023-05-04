package com.checkout.issuing;

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
import com.checkout.issuing.testing.requests.CardAuthorizationRequest;
import com.checkout.issuing.testing.responses.CardAuthorizationResponse;
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
}