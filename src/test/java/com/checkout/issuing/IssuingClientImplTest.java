package com.checkout.issuing;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.IdResponse;
import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.cardholders.CardholderDetailsResponse;
import com.checkout.issuing.cardholders.CardholderAccessTokenRequest;
import com.checkout.issuing.cardholders.CardholderAccessTokenResponse;
import com.checkout.issuing.cardholders.CardholderRequest;
import com.checkout.issuing.cardholders.CardholderUpdateRequest;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cardholders.CardholderUpdateResponse;
import com.checkout.issuing.cards.requests.create.VirtualCardRequest;
import com.checkout.issuing.cards.requests.credentials.CardCredentialsQuery;
import com.checkout.issuing.cards.requests.enrollment.PasswordThreeDSEnrollmentRequest;
import com.checkout.issuing.cards.requests.enrollment.ThreeDSUpdateRequest;
import com.checkout.issuing.cards.requests.renew.RenewCardRequest;
import com.checkout.issuing.cards.requests.revocation.ScheduleRevocationRequest;
import com.checkout.issuing.cards.requests.revoke.RevokeCardRequest;
import com.checkout.issuing.cards.requests.suspend.SuspendCardRequest;
import com.checkout.issuing.cards.requests.update.UpdateCardRequest;
import com.checkout.issuing.cards.responses.CardDetailsResponse;
import com.checkout.issuing.cards.responses.CardResponse;
import com.checkout.issuing.cards.responses.credentials.CardCredentialsResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSEnrollmentDetailsResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSEnrollmentResponse;
import com.checkout.issuing.cards.responses.enrollment.ThreeDSUpdateResponse;
import com.checkout.issuing.cards.responses.renew.RenewCardResponse;
import com.checkout.issuing.cards.responses.update.UpdateCardResponse;
import com.checkout.issuing.controls.requests.create.CardControlRequest;
import com.checkout.issuing.controls.requests.query.CardControlsQuery;
import com.checkout.issuing.controls.requests.update.UpdateCardControlRequest;
import com.checkout.issuing.controls.responses.create.CardControlResponse;
import com.checkout.issuing.controls.responses.query.CardControlsQueryResponse;
import com.checkout.issuing.controls.requests.controlgroup.CreateControlGroupRequest;
import com.checkout.issuing.controls.requests.controlgroup.ControlGroupQuery;
import com.checkout.issuing.controls.responses.controlgroup.ControlGroupResponse;
import com.checkout.issuing.controls.responses.controlgroup.ControlGroupsQueryResponse;
import com.checkout.issuing.controls.requests.controlprofile.CreateControlProfileRequest;
import com.checkout.issuing.controls.requests.controlprofile.ControlProfileQuery;
import com.checkout.issuing.controls.requests.controlprofile.UpdateControlProfileRequest;
import com.checkout.issuing.controls.responses.controlprofile.ControlProfileResponse;
import com.checkout.issuing.controls.responses.controlprofile.ControlProfilesQueryResponse;
import com.checkout.issuing.disputes.requests.CreateDisputeRequest;
import com.checkout.issuing.disputes.requests.EscalateDisputeRequest;
import com.checkout.issuing.disputes.requests.SubmitDisputeRequest;
import com.checkout.issuing.disputes.responses.DisputeResponse;
import com.checkout.issuing.transactions.requests.TransactionsQuery;
import com.checkout.issuing.transactions.responses.TransactionsListResponse;
import com.checkout.issuing.transactions.responses.TransactionsSingleResponse;
import com.checkout.issuing.transactions.requests.TransactionsQuery;
import com.checkout.issuing.transactions.responses.TransactionsListResponse;
import com.checkout.issuing.transactions.responses.TransactionsSingleResponse;
import com.checkout.issuing.testing.requests.CardAuthorizationClearingRequest;
import com.checkout.issuing.testing.requests.CardAuthorizationIncrementingRequest;
import com.checkout.issuing.testing.requests.CardAuthorizationRefundsRequest;
import com.checkout.issuing.testing.requests.CardAuthorizationRequest;
import com.checkout.issuing.testing.requests.CardAuthorizationReversalRequest;
import com.checkout.issuing.testing.responses.CardAuthorizationIncrementingResponse;
import com.checkout.issuing.testing.responses.CardAuthorizationResponse;
import com.checkout.issuing.testing.responses.CardAuthorizationReversalResponse;
import com.checkout.payments.VoidResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IssuingClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private IssuingClient client;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new IssuingClientImpl(apiClient, configuration);
    }

    @Nested
    @DisplayName("Cardholders")
    class Cardholders {
        @Test
        void shouldCreateCardholder() throws ExecutionException, InterruptedException {
            final CardholderRequest request = createCardholderRequest();
            final CardholderResponse response = createCardholderResponse();

            when(apiClient.postAsync(
                    "issuing/cardholders",
                    authorization,
                    CardholderResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderResponse> future = client.createCardholder(request);

            validateCardholderResponse(response, future.get());
        }

        @Test
        void shouldGetCardholderDetails() throws ExecutionException, InterruptedException {
            final CardholderDetailsResponse response = createCardholderDetailsResponse();

            when(apiClient.getAsync(
                    "issuing/cardholders/cardholder_id",
                    authorization,
                    CardholderDetailsResponse.class))
            .thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderDetailsResponse> future = client.getCardholder("cardholder_id");

            validateCardholderDetailsResponse(response, future.get());
        }

        @Test
        void shouldGetCardholderCards() throws ExecutionException, InterruptedException {
            final CardholderCardsResponse response = createCardholderCardsResponse();

            when(apiClient.getAsync(
                    "issuing/cardholders/cardholder_id/cards",
                    authorization,
                    CardholderCardsResponse.class))
                    .thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderCardsResponse> future = client.getCardholderCards("cardholder_id");

            validateCardholderCardsResponse(response, future.get());
        }

        @Test
        void shouldRequestCardholderAccessToken() throws ExecutionException, InterruptedException {
            final CardholderAccessTokenRequest request = createCardholderAccessTokenRequest();
            final CardholderAccessTokenResponse response = createCardholderAccessTokenResponse();

            when(apiClient.postAsync(
                    eq("issuing/access/connect/token"),
                    eq(authorization),
                    eq(CardholderAccessTokenResponse.class),
                    any(UrlEncodedFormEntity.class),
                    eq(null)
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderAccessTokenResponse> future = client.requestCardholderAccessToken(request);

            validateCardholderAccessTokenResponse(response, future.get());
        }

        @Test
        void shouldUpdateCardholder() throws ExecutionException, InterruptedException {
            final CardholderUpdateRequest request = createCardholderUpdateRequest();
            final CardholderUpdateResponse response = createCardholderUpdateResponse();

            when(apiClient.patchAsync(
                    "issuing/cardholders/cardholder_id",
                    authorization,
                    CardholderUpdateResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderUpdateResponse> future = client.updateCardholder("cardholder_id", request);

            validateCardholderUpdateResponse(response, future.get());
        }
    }

    @Nested
    @DisplayName("Cards")
    class Cards {
        @Test
        void shouldCreateCard() throws ExecutionException, InterruptedException {
            final VirtualCardRequest request = createVirtualCardRequest();
            final CardResponse response = createCardResponse();

            when(apiClient.postAsync(
                    "issuing/cards",
                    authorization,
                    CardResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardResponse> future = client.createCard(request);

            validateCardResponse(response, future.get());
        }

        @Test
        void shouldGetCardDetails() throws ExecutionException, InterruptedException {
            final CardDetailsResponse response = createCardDetailsResponse();

            when(apiClient.getAsync(
                    "issuing/cards/card_id",
                    authorization,
                    CardDetailsResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardDetailsResponse> future = client.getCardDetails("card_id");

            validateCardDetailsResponse(response, future.get());
        }

        @Test
        void shouldEnrollCardIntoThreeDS() throws ExecutionException, InterruptedException {
            final PasswordThreeDSEnrollmentRequest request = createPasswordThreeDSEnrollmentRequest();
            final ThreeDSEnrollmentResponse response = createThreeDSEnrollmentResponse();

            when(apiClient.postAsync(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSEnrollmentResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ThreeDSEnrollmentResponse> future = client.enrollThreeDS("card_id", request);

            validateThreeDSEnrollmentResponse(response, future.get());
        }

        @Test
        void shouldUpdateThreeDSEnrollment() throws ExecutionException, InterruptedException {
            final ThreeDSUpdateRequest request = createThreeDSUpdateRequest();
            final ThreeDSUpdateResponse response = createThreeDSUpdateResponse();

            when(apiClient.patchAsync(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSUpdateResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ThreeDSUpdateResponse> future = client.updateThreeDS("card_id", request);

            validateThreeDSUpdateResponse(response, future.get());
        }

        @Test
        void shouldGetThreeDSDetails() throws ExecutionException, InterruptedException {
            final ThreeDSEnrollmentDetailsResponse response = createThreeDSEnrollmentDetailsResponse();

            when(apiClient.getAsync(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSEnrollmentDetailsResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ThreeDSEnrollmentDetailsResponse> future = client.getCardThreeDSDetails("card_id");

            validateThreeDSEnrollmentDetailsResponse(response, future.get());
        }

        @Test
        void shouldActivateCard() throws ExecutionException, InterruptedException {
            final VoidResponse response = createVoidResponse();

            when(apiClient.postAsync(
                    "issuing/cards/card_id/activate",
                    authorization,
                    VoidResponse.class,
                    null,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.activateCard("card_id");

            validateVoidResponse(response, future.get());
        }

        @Test
        void shouldGetCardCredentials() throws ExecutionException, InterruptedException {
            final CardCredentialsQuery query = createCardCredentialsQuery();
            final CardCredentialsResponse response = createCardCredentialsResponse();

            when(apiClient.queryAsync(
                    "issuing/cards/card_id/credentials",
                    authorization,
                    query,
                    CardCredentialsResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardCredentialsResponse> future = client.getCardCredentials("card_id", query);

            validateCardCredentialsResponse(response, future.get());
        }

        @Test
        void shouldRevokeCard() throws ExecutionException, InterruptedException {
            final RevokeCardRequest request = createRevokeCardRequest();
            final VoidResponse response = createVoidResponse();

            when(apiClient.postAsync(
                    "issuing/cards/card_id/revoke",
                    authorization,
                    VoidResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.revokeCard("card_id", request);

            validateVoidResponse(response, future.get());
        }

        @Test
        void shouldSuspendCard() throws ExecutionException, InterruptedException {
            final SuspendCardRequest request = createSuspendCardRequest();
            final VoidResponse response = createVoidResponse();

            when(apiClient.postAsync(
                    "issuing/cards/card_id/suspend",
                    authorization,
                    VoidResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.suspendCard("card_id", request);

            validateVoidResponse(response, future.get());
        }

        @Test
        void shouldUpdateCard() throws ExecutionException, InterruptedException {
            final UpdateCardRequest request = createUpdateCardRequest();
            final UpdateCardResponse response = createUpdateCardResponse();

            when(apiClient.patchAsync(
                    "issuing/cards/card_id",
                    authorization,
                    UpdateCardResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<UpdateCardResponse> future = client.updateCard("card_id", request);

            validateUpdateCardResponse(response, future.get());
        }

        @Test
        void shouldRenewCard() throws ExecutionException, InterruptedException {
            final RenewCardRequest request = createRenewCardRequest();
            final RenewCardResponse response = createRenewCardResponse();

            when(apiClient.postAsync(
                    "issuing/cards/card_id/renew",
                    authorization,
                    RenewCardResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<RenewCardResponse> future = client.renewCard("card_id", request);

            validateRenewCardResponse(response, future.get());
        }

        @Test
        void shouldScheduleCardRevocation() throws ExecutionException, InterruptedException {
            final ScheduleRevocationRequest request = createScheduleRevocationRequest();
            final VoidResponse response = createVoidResponse();

            when(apiClient.postAsync(
                    "issuing/cards/card_id/schedule-revocation",
                    authorization,
                    VoidResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.scheduleCardRevocation("card_id", request);

            validateVoidResponse(response, future.get());
        }

        @Test
        void shouldDeleteScheduledRevocation() throws ExecutionException, InterruptedException {
            final VoidResponse response = createVoidResponse();

            when(apiClient.deleteAsync(
                    "issuing/cards/card_id/schedule-revocation",
                    authorization,
                    VoidResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.deleteScheduledRevocation("card_id");

            validateVoidResponse(response, future.get());
        }
    }

    @Nested
    @DisplayName("Controls")
    class Controls {
        @Test
        void shouldCreateControl() throws ExecutionException, InterruptedException {
            final CardControlRequest request = createCardControlRequest();
            final CardControlResponse response = createCardControlResponse();

            when(apiClient.postAsync(
                    "issuing/controls",
                    authorization,
                    CardControlResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardControlResponse> future = client.createControl(request);

            validateCardControlResponse(response, future.get());
        }

        @Test
        void shouldGetCardControls() throws ExecutionException, InterruptedException {
            final CardControlsQuery query = createCardControlsQuery();
            final CardControlsQueryResponse response = createCardControlsQueryResponse();

            when(apiClient.queryAsync(
                    "issuing/controls",
                    authorization,
                    query,
                    CardControlsQueryResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardControlsQueryResponse> future = client.getCardControls(query);

            validateCardControlsQueryResponse(response, future.get());
        }

        @Test
        void shouldGetCardControlDetails() throws ExecutionException, InterruptedException {
            final CardControlResponse response = createCardControlResponse();

            when(apiClient.getAsync(
                    "issuing/controls/control_id",
                    authorization,
                    CardControlResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardControlResponse> future = client.getCardControlDetails("control_id");

            validateCardControlResponse(response, future.get());
        }

        @Test
        void shouldUpdateCardControl() throws ExecutionException, InterruptedException {
            final UpdateCardControlRequest request = createUpdateCardControlRequest();
            final CardControlResponse response = createCardControlResponse();

            when(apiClient.putAsync(
                    "issuing/controls/control_id",
                    authorization,
                    CardControlResponse.class,
                    request
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardControlResponse> future = client.updateCardControl("control_id", request);

            validateCardControlResponse(response, future.get());
        }

        @Test
        void shouldRemoveCardControlDetails() throws ExecutionException, InterruptedException {
            final IdResponse response = createIdResponse();

            when(apiClient.deleteAsync(
                    "issuing/controls/control_id",
                    authorization,
                    IdResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<IdResponse> future = client.removeCardControl("control_id");

            validateIdResponse(response, future.get());
        }
    }

    @Nested
    @DisplayName("Testing")
    class Testing {
        @Test
        void shouldSimulateAuthorization() throws ExecutionException, InterruptedException {
            final CardAuthorizationRequest request = createCardAuthorizationRequest();
            final CardAuthorizationResponse response = createCardAuthorizationResponse();

            when(apiClient.postAsync(
                    "issuing/simulate/authorizations",
                    authorization,
                    CardAuthorizationResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardAuthorizationResponse> future = client.simulateAuthorization(request);

            validateCardAuthorizationResponse(response, future.get());
        }

        @Test
        void shouldSimulateAuthorizationIncrementingAuthorization() throws ExecutionException, InterruptedException {
            final CardAuthorizationIncrementingRequest request = createCardAuthorizationIncrementingRequest();
            final CardAuthorizationIncrementingResponse response = createCardAuthorizationIncrementingResponse();

            when(apiClient.postAsync(
                    "issuing/simulate/authorizations/authorization_id/authorizations",
                    authorization,
                    CardAuthorizationIncrementingResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardAuthorizationIncrementingResponse> future =
                    client.simulateIncrementingAuthorization("authorization_id", request);

            validateCardAuthorizationIncrementingResponse(response, future.get());
        }

        @Test
        void shouldSimulateAuthorizationClearing() throws ExecutionException, InterruptedException {
            final CardAuthorizationClearingRequest request = createCardAuthorizationClearingRequest();
            final EmptyResponse response = createEmptyResponse();

            when(apiClient.postAsync(
                    "issuing/simulate/authorizations/authorization_id/presentments",
                    authorization,
                    EmptyResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<EmptyResponse> future =
                    client.simulateClearing("authorization_id", request);

            validateEmptyResponse(response, future.get());
        }

        @Test
        void shouldSimulateAuthorizationRefund() throws ExecutionException, InterruptedException {
            final CardAuthorizationRefundsRequest request = createCardAuthorizationRefundsRequest();
            final EmptyResponse response = createEmptyResponse();

            when(apiClient.postAsync(
                    "issuing/simulate/authorizations/authorization_id/refunds",
                    authorization,
                    EmptyResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<EmptyResponse> future =
                    client.simulateRefund("authorization_id", request);

            validateEmptyResponse(response, future.get());
        }

        @Test
        void shouldSimulateAuthorizationReversal() throws ExecutionException, InterruptedException {
            final CardAuthorizationReversalRequest request = createCardAuthorizationReversalRequest();
            final CardAuthorizationReversalResponse response = createCardAuthorizationReversalResponse();

            when(apiClient.postAsync(
                    "issuing/simulate/authorizations/authorization_id/reversals",
                    authorization,
                    CardAuthorizationReversalResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardAuthorizationReversalResponse> future =
                    client.simulateReversal("authorization_id", request);

            validateCardAuthorizationReversalResponse(response, future.get());
        }
    }

    @Nested
    @DisplayName("Control Groups")
    class ControlGroups {
        @Test
        void shouldCreateControlGroup() throws ExecutionException, InterruptedException {
            final CreateControlGroupRequest request = createCreateControlGroupRequest();
            final ControlGroupResponse response = createControlGroupResponse();

            when(apiClient.postAsync(
                    "issuing/controls/control-groups",
                    authorization,
                    ControlGroupResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ControlGroupResponse> future = client.createControlGroup(request);

            validateControlGroupResponse(response, future.get());
        }

        @Test
        void shouldGetControlGroupDetails() throws ExecutionException, InterruptedException {
            final ControlGroupResponse response = createControlGroupResponse();

            when(apiClient.getAsync(
                    "issuing/controls/control-groups/group_id",
                    authorization,
                    ControlGroupResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ControlGroupResponse> future = client.getControlGroupDetails("group_id");

            validateControlGroupResponse(response, future.get());
        }

        @Test
        void shouldQueryControlGroups() throws ExecutionException, InterruptedException {
            final ControlGroupQuery query = createControlGroupQuery();
            final ControlGroupsQueryResponse response = createControlGroupsQueryResponse();

            when(apiClient.queryAsync(
                    "issuing/controls/control-groups",
                    authorization,
                    query,
                    ControlGroupsQueryResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ControlGroupsQueryResponse> future = client.getControlGroups(query);

            validateControlGroupsQueryResponse(response, future.get());
        }

        @Test
        void shouldRemoveControlGroup() throws ExecutionException, InterruptedException {
            final IdResponse response = createIdResponse();

            when(apiClient.deleteAsync(
                    "issuing/controls/control-groups/group_id",
                    authorization,
                    IdResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<IdResponse> future = client.removeControlGroup("group_id");

            validateIdResponse(response, future.get());
        }
    }

    @Nested
    @DisplayName("Control Profiles")
    class ControlProfiles {
        @Test
        void shouldCreateControlProfile() throws ExecutionException, InterruptedException {
            final CreateControlProfileRequest request = createCreateControlProfileRequest();
            final ControlProfileResponse response = createControlProfileResponse();

            when(apiClient.postAsync(
                    "issuing/controls/control-profiles",
                    authorization,
                    ControlProfileResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ControlProfileResponse> future = client.createControlProfile(request);

            validateControlProfileResponse(response, future.get());
        }

        @Test
        void shouldGetControlProfileDetails() throws ExecutionException, InterruptedException {
            final ControlProfileResponse response = createControlProfileResponse();

            when(apiClient.getAsync(
                    "issuing/controls/control-profiles/profile_id",
                    authorization,
                    ControlProfileResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ControlProfileResponse> future = client.getControlProfileDetails("profile_id");

            validateControlProfileResponse(response, future.get());
        }

        @Test
        void shouldQueryControlProfiles() throws ExecutionException, InterruptedException {
            final ControlProfileQuery query = createControlProfileQuery();
            final ControlProfilesQueryResponse response = createControlProfilesQueryResponse();

            when(apiClient.queryAsync(
                    "issuing/controls/control-profiles",
                    authorization,
                    query,
                    ControlProfilesQueryResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ControlProfilesQueryResponse> future = client.getControlProfiles(query);

            validateControlProfilesQueryResponse(response, future.get());
        }

        @Test
        void shouldUpdateControlProfile() throws ExecutionException, InterruptedException {
            final UpdateControlProfileRequest request = createUpdateControlProfileRequest();
            final ControlProfileResponse response = createControlProfileResponse();

            when(apiClient.patchAsync(
                    "issuing/controls/control-profiles/profile_id",
                    authorization,
                    ControlProfileResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ControlProfileResponse> future = client.updateControlProfile("profile_id", request);

            validateControlProfileResponse(response, future.get());
        }

        @Test
        void shouldRemoveControlProfile() throws ExecutionException, InterruptedException {
            final EmptyResponse response = createEmptyResponse();

            when(apiClient.deleteAsync(
                    "issuing/controls/control-profiles/profile_id",
                    authorization,
                    EmptyResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<EmptyResponse> future = client.removeControlProfile("profile_id");

            validateEmptyResponse(response, future.get());
        }

        @Test
        void shouldAddTargetToControlProfile() throws ExecutionException, InterruptedException {
            final VoidResponse response = createVoidResponse();

            when(apiClient.postAsync(
                    "issuing/controls/control-profiles/profile_id/add/target_id",
                    authorization,
                    VoidResponse.class,
                    null,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.addTargetToControlProfile("profile_id", "target_id");
            validateVoidResponse(response, future.get());
        }

        @Test
        void shouldRemoveTargetFromControlProfile() throws ExecutionException, InterruptedException {
            final VoidResponse response = createVoidResponse();

            when(apiClient.postAsync(
                    "issuing/controls/control-profiles/profile_id/remove/target_id",
                    authorization,
                    VoidResponse.class,
                    null,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.removeTargetFromControlProfile("profile_id", "target_id");

            validateVoidResponse(response, future.get());
        }
    }

    @DisplayName("Disputes")
    class Disputes {
        @Test
        void shouldCreateDispute() throws ExecutionException, InterruptedException {
            final CreateDisputeRequest request = createCreateDisputeRequest();
            final DisputeResponse response = createDisputeResponse();

            when(apiClient.postAsync(
                    "issuing/disputes",
                    authorization,
                    DisputeResponse.class,
                    request,
                    "idempotencyKey"
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<DisputeResponse> future = client.createDispute(request, "idempotencyKey");

            validateDisputeResponse(response, future.get());
        }

        @Test
        void shouldGetDispute() throws ExecutionException, InterruptedException {
            final DisputeResponse response = createDisputeResponse();

            when(apiClient.getAsync(
                    "issuing/disputes/dispute_id",
                    authorization,
                    DisputeResponse.class))
                    .thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<DisputeResponse> future = client.getDispute("dispute_id");

            validateDisputeResponse(response, future.get());
        }

        @Test
        void shouldCancelDispute() throws ExecutionException, InterruptedException {
            final VoidResponse response = createVoidResponse();

            when(apiClient.postAsync(
                    "issuing/disputes/dispute_id/cancel",
                    authorization,
                    VoidResponse.class,
                    null,
                    "idempotencyKey"
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.cancelDispute("dispute_id", "idempotencyKey");

            validateVoidResponse(response, future.get());
        }

        void shouldEscalateDispute() throws ExecutionException, InterruptedException {
            final EscalateDisputeRequest request = createEscalateDisputeRequest();
            final VoidResponse response = createVoidResponse();

            when(apiClient.postAsync(
                    "issuing/disputes/dispute_id/escalate",
                    authorization,
                    VoidResponse.class,
                    request,
                    "idempotencyKey"
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.escalateDispute("dispute_id", "idempotencyKey", request);

            validateVoidResponse(response, future.get());
        }

        @Test
        void shouldSubmitDispute() throws ExecutionException, InterruptedException {
            final SubmitDisputeRequest request = createSubmitDisputeRequest();
            final DisputeResponse response = createDisputeResponse();

            when(apiClient.postAsync(
                    "issuing/disputes/dispute_id/submit",
                    authorization,
                    DisputeResponse.class,
                    request,
                    "idempotencyKey"
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<DisputeResponse> future = client.submitDispute("dispute_id", "idempotencyKey", request);

            validateDisputeResponse(response, future.get());
        }
    }

    // Synchronous methods
    @Nested
    @DisplayName("Cardholders Sync")
    class CardholdersSync {
        @Test
        void shouldCreateCardholderSync() {
            final CardholderRequest request = createCardholderRequest();
            final CardholderResponse expectedResponse = createCardholderResponse();

            when(apiClient.post(
                    "issuing/cardholders",
                    authorization,
                    CardholderResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final CardholderResponse actualResponse = client.createCardholderSync(request);

            validateCardholderResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetCardholderDetailsSync() {
            final CardholderDetailsResponse expectedResponse = createCardholderDetailsResponse();

            when(apiClient.get(
                    "issuing/cardholders/cardholder_id",
                    authorization,
                    CardholderDetailsResponse.class))
            .thenReturn(expectedResponse);

            final CardholderDetailsResponse actualResponse = client.getCardholderSync("cardholder_id");

            validateCardholderDetailsResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetCardholderCardsSync() {
            final CardholderCardsResponse expectedResponse = createCardholderCardsResponse();

            when(apiClient.get(
                    "issuing/cardholders/cardholder_id/cards",
                    authorization,
                    CardholderCardsResponse.class))
                    .thenReturn(expectedResponse);

            final CardholderCardsResponse actualResponse = client.getCardholderCardsSync("cardholder_id");

            validateCardholderCardsResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldRequestCardholderAccessTokenSync() {
            final CardholderAccessTokenRequest request = createCardholderAccessTokenRequest();
            final CardholderAccessTokenResponse expectedResponse = createCardholderAccessTokenResponse();

            when(apiClient.post(
                    eq("issuing/access/connect/token"),
                    eq(authorization),
                    eq(CardholderAccessTokenResponse.class),
                    any(UrlEncodedFormEntity.class),
                    eq(null)
            )).thenReturn(expectedResponse);

            final CardholderAccessTokenResponse actualResponse = client.requestCardholderAccessTokenSync(request);

            validateCardholderAccessTokenResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldUpdateCardholderSync() {
            final CardholderUpdateRequest request = createCardholderUpdateRequest();
            final CardholderUpdateResponse expectedResponse = createCardholderUpdateResponse();

            when(apiClient.patch(
                    "issuing/cardholders/cardholder_id",
                    authorization,
                    CardholderUpdateResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final CardholderUpdateResponse actualResponse = client.updateCardholderSync("cardholder_id", request);

            validateCardholderUpdateResponse(expectedResponse, actualResponse);
        }
    }

    @Nested
    @DisplayName("Cards Sync")
    class CardsSync {
        @Test
        void shouldCreateCardSync() {
            final VirtualCardRequest request = createVirtualCardRequest();
            final CardResponse expectedResponse = createCardResponse();

            when(apiClient.post(
                    "issuing/cards",
                    authorization,
                    CardResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final CardResponse actualResponse = client.createCardSync(request);

            validateCardResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetCardDetailsSync() {
            final CardDetailsResponse expectedResponse = createCardDetailsResponse();

            when(apiClient.get(
                    "issuing/cards/card_id",
                    authorization,
                    CardDetailsResponse.class
            )).thenReturn(expectedResponse);

            final CardDetailsResponse actualResponse = client.getCardDetailsSync("card_id");

            validateCardDetailsResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldEnrollCardIntoThreeDSSync() {
            final PasswordThreeDSEnrollmentRequest request = createPasswordThreeDSEnrollmentRequest();
            final ThreeDSEnrollmentResponse expectedResponse = createThreeDSEnrollmentResponse();

            when(apiClient.post(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSEnrollmentResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final ThreeDSEnrollmentResponse actualResponse = client.enrollThreeDSSync("card_id", request);

            validateThreeDSEnrollmentResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldUpdateThreeDSEnrollmentSync() {
            final ThreeDSUpdateRequest request = createThreeDSUpdateRequest();
            final ThreeDSUpdateResponse expectedResponse = createThreeDSUpdateResponse();

            when(apiClient.patch(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSUpdateResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final ThreeDSUpdateResponse actualResponse = client.updateThreeDSSync("card_id", request);

            validateThreeDSUpdateResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetThreeDSDetailsSync() {
            final ThreeDSEnrollmentDetailsResponse expectedResponse = createThreeDSEnrollmentDetailsResponse();

            when(apiClient.get(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSEnrollmentDetailsResponse.class
            )).thenReturn(expectedResponse);

            final ThreeDSEnrollmentDetailsResponse actualResponse = client.getCardThreeDSDetailsSync("card_id");

            validateThreeDSEnrollmentDetailsResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldActivateCardSync() {
            final VoidResponse expectedResponse = createVoidResponse();

            when(apiClient.post(
                    "issuing/cards/card_id/activate",
                    authorization,
                    VoidResponse.class,
                    null,
                    null
            )).thenReturn(expectedResponse);

            final VoidResponse actualResponse = client.activateCardSync("card_id");

            validateVoidResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetCardCredentialsSync() {
            final CardCredentialsQuery query = createCardCredentialsQuery();
            final CardCredentialsResponse expectedResponse = createCardCredentialsResponse();

            when(apiClient.query(
                    "issuing/cards/card_id/credentials",
                    authorization,
                    query,
                    CardCredentialsResponse.class
            )).thenReturn(expectedResponse);

            final CardCredentialsResponse actualResponse = client.getCardCredentialsSync("card_id", query);

            validateCardCredentialsResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldRevokeCardSync() {
            final RevokeCardRequest request = createRevokeCardRequest();
            final VoidResponse expectedResponse = createVoidResponse();

            when(apiClient.post(
                    "issuing/cards/card_id/revoke",
                    authorization,
                    VoidResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final VoidResponse actualResponse = client.revokeCardSync("card_id", request);

            validateVoidResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldSuspendCardSync() {
            final SuspendCardRequest request = createSuspendCardRequest();
            final VoidResponse expectedResponse = createVoidResponse();

            when(apiClient.post(
                    "issuing/cards/card_id/suspend",
                    authorization,
                    VoidResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final VoidResponse actualResponse = client.suspendCardSync("card_id", request);

            validateVoidResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldUpdateCardSync() {
            final UpdateCardRequest request = createUpdateCardRequest();
            final UpdateCardResponse expectedResponse = createUpdateCardResponse();

            when(apiClient.patch(
                    "issuing/cards/card_id",
                    authorization,
                    UpdateCardResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final UpdateCardResponse actualResponse = client.updateCardSync("card_id", request);

            validateUpdateCardResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldRenewCardSync() {
            final RenewCardRequest request = createRenewCardRequest();
            final RenewCardResponse expectedResponse = createRenewCardResponse();

            when(apiClient.post(
                    "issuing/cards/card_id/renew",
                    authorization,
                    RenewCardResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final RenewCardResponse actualResponse = client.renewCardSync("card_id", request);

            validateRenewCardResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldScheduleCardRevocationSync() {
            final ScheduleRevocationRequest request = createScheduleRevocationRequest();
            final VoidResponse expectedResponse = createVoidResponse();

            when(apiClient.post(
                    "issuing/cards/card_id/schedule-revocation",
                    authorization,
                    VoidResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final VoidResponse actualResponse = client.scheduleCardRevocationSync("card_id", request);

            validateVoidResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldDeleteScheduledRevocationSync() {
            final VoidResponse expectedResponse = createVoidResponse();

            when(apiClient.delete(
                    "issuing/cards/card_id/schedule-revocation",
                    authorization,
                    VoidResponse.class
            )).thenReturn(expectedResponse);

            final VoidResponse actualResponse = client.deleteScheduledRevocationSync("card_id");

            validateVoidResponse(expectedResponse, actualResponse);
        }
    }

    @Nested
    @DisplayName("Controls Sync")
    class ControlsSync {
        @Test
        void shouldCreateControlSync() {
            final CardControlRequest request = createCardControlRequest();
            final CardControlResponse expectedResponse = createCardControlResponse();

            when(apiClient.post(
                    "issuing/controls",
                    authorization,
                    CardControlResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final CardControlResponse actualResponse = client.createControlSync(request);

            validateCardControlResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetCardControlsSync() {
            final CardControlsQuery query = createCardControlsQuery();
            final CardControlsQueryResponse expectedResponse = createCardControlsQueryResponse();

            when(apiClient.query(
                    "issuing/controls",
                    authorization,
                    query,
                    CardControlsQueryResponse.class
            )).thenReturn(expectedResponse);

            final CardControlsQueryResponse actualResponse = client.getCardControlsSync(query);

            validateCardControlsQueryResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetCardControlDetailsSync() {
            final CardControlResponse expectedResponse = createCardControlResponse();

            when(apiClient.get(
                    "issuing/controls/control_id",
                    authorization,
                    CardControlResponse.class
            )).thenReturn(expectedResponse);

            final CardControlResponse actualResponse = client.getCardControlDetailsSync("control_id");

            validateCardControlResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldUpdateCardControlSync() {
            final UpdateCardControlRequest request = createUpdateCardControlRequest();
            final CardControlResponse expectedResponse = createCardControlResponse();

            when(apiClient.put(
                    "issuing/controls/control_id",
                    authorization,
                    CardControlResponse.class,
                    request
            )).thenReturn(expectedResponse);

            final CardControlResponse actualResponse = client.updateCardControlSync("control_id", request);

            validateCardControlResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldRemoveCardControlDetailsSync() {
            final IdResponse expectedResponse = createIdResponse();

            when(apiClient.delete(
                    "issuing/controls/control_id",
                    authorization,
                    IdResponse.class
            )).thenReturn(expectedResponse);

            final IdResponse actualResponse = client.removeCardControlSync("control_id");

            validateIdResponse(expectedResponse, actualResponse);
        }
    }

    @Nested
    @DisplayName("Testing Sync")
    class TestingSync {
        @Test
        void shouldSimulateAuthorizationSync() {
            final CardAuthorizationRequest request = createCardAuthorizationRequest();
            final CardAuthorizationResponse expectedResponse = createCardAuthorizationResponse();

            when(apiClient.post(
                    "issuing/simulate/authorizations",
                    authorization,
                    CardAuthorizationResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final CardAuthorizationResponse actualResponse = client.simulateAuthorizationSync(request);

            validateCardAuthorizationResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldSimulateAuthorizationIncrementingAuthorizationSync() {
            final CardAuthorizationIncrementingRequest request = createCardAuthorizationIncrementingRequest();
            final CardAuthorizationIncrementingResponse expectedResponse = createCardAuthorizationIncrementingResponse();

            when(apiClient.post(
                    "issuing/simulate/authorizations/authorization_id/authorizations",
                    authorization,
                    CardAuthorizationIncrementingResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final CardAuthorizationIncrementingResponse actualResponse =
                    client.simulateIncrementingAuthorizationSync("authorization_id", request);

            validateCardAuthorizationIncrementingResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldSimulateAuthorizationClearingSync() {
            final CardAuthorizationClearingRequest request = createCardAuthorizationClearingRequest();
            final EmptyResponse expectedResponse = createEmptyResponse();

            when(apiClient.post(
                    "issuing/simulate/authorizations/authorization_id/presentments",
                    authorization,
                    EmptyResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final EmptyResponse actualResponse = client.simulateClearingSync("authorization_id", request);

            validateEmptyResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldSimulateAuthorizationRefundSync() {
            final CardAuthorizationRefundsRequest request = createCardAuthorizationRefundsRequest();
            final EmptyResponse expectedResponse = createEmptyResponse();

            when(apiClient.post(
                    "issuing/simulate/authorizations/authorization_id/refunds",
                    authorization,
                    EmptyResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final EmptyResponse actualResponse = client.simulateRefundSync("authorization_id", request);

            validateEmptyResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldSimulateAuthorizationReversalSync() {
            final CardAuthorizationReversalRequest request = createCardAuthorizationReversalRequest();
            final CardAuthorizationReversalResponse expectedResponse = createCardAuthorizationReversalResponse();

            when(apiClient.post(
                    "issuing/simulate/authorizations/authorization_id/reversals",
                    authorization,
                    CardAuthorizationReversalResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final CardAuthorizationReversalResponse actualResponse =
                    client.simulateReversalSync("authorization_id", request);

            validateCardAuthorizationReversalResponse(expectedResponse, actualResponse);
        }
    }

    @Nested
    @DisplayName("Control Groups Sync")
    class ControlGroupsSync {
        @Test
        void shouldCreateControlGroupSync() {
            final CreateControlGroupRequest request = createCreateControlGroupRequest();
            final ControlGroupResponse expectedResponse = createControlGroupResponse();

            when(apiClient.post(
                    "issuing/controls/control-groups",
                    authorization,
                    ControlGroupResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final ControlGroupResponse actualResponse = client.createControlGroupSync(request);

            validateControlGroupResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetControlGroupDetailsSync() {
            final ControlGroupResponse expectedResponse = createControlGroupResponse();

            when(apiClient.get(
                    "issuing/controls/control-groups/group_id",
                    authorization,
                    ControlGroupResponse.class
            )).thenReturn(expectedResponse);

            final ControlGroupResponse actualResponse = client.getControlGroupDetailsSync("group_id");

            validateControlGroupResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldQueryControlGroupsSync() {
            final ControlGroupQuery query = createControlGroupQuery();
            final ControlGroupsQueryResponse expectedResponse = createControlGroupsQueryResponse();

            when(apiClient.query(
                    "issuing/controls/control-groups",
                    authorization,
                    query,
                    ControlGroupsQueryResponse.class
            )).thenReturn(expectedResponse);

            final ControlGroupsQueryResponse actualResponse = client.getControlGroupsSync(query);

            validateControlGroupsQueryResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldRemoveControlGroupSync() {
            final IdResponse expectedResponse = createIdResponse();

            when(apiClient.delete(
                    "issuing/controls/control-groups/group_id",
                    authorization,
                    IdResponse.class
            )).thenReturn(expectedResponse);

            final IdResponse actualResponse = client.removeControlGroupSync("group_id");

            validateIdResponse(expectedResponse, actualResponse);
        }
    }

    @Nested
    @DisplayName("Control Profiles Sync")
    class ControlProfilesSync {
        @Test
        void shouldCreateControlProfileSync() {
            final CreateControlProfileRequest request = createCreateControlProfileRequest();
            final ControlProfileResponse expectedResponse = createControlProfileResponse();

            when(apiClient.post(
                    "issuing/controls/control-profiles",
                    authorization,
                    ControlProfileResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final ControlProfileResponse actualResponse = client.createControlProfileSync(request);

            validateControlProfileResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetControlProfileDetailsSync() {
            final ControlProfileResponse expectedResponse = createControlProfileResponse();

            when(apiClient.get(
                    "issuing/controls/control-profiles/profile_id",
                    authorization,
                    ControlProfileResponse.class
            )).thenReturn(expectedResponse);

            final ControlProfileResponse actualResponse = client.getControlProfileDetailsSync("profile_id");

            validateControlProfileResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldQueryControlProfilesSync() {
            final ControlProfileQuery query = createControlProfileQuery();
            final ControlProfilesQueryResponse expectedResponse = createControlProfilesQueryResponse();

            when(apiClient.query(
                    "issuing/controls/control-profiles",
                    authorization,
                    query,
                    ControlProfilesQueryResponse.class
            )).thenReturn(expectedResponse);

            final ControlProfilesQueryResponse actualResponse = client.getControlProfilesSync(query);

            validateControlProfilesQueryResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldUpdateControlProfileSync() {
            final UpdateControlProfileRequest request = createUpdateControlProfileRequest();
            final ControlProfileResponse expectedResponse = createControlProfileResponse();

            when(apiClient.patch(
                    "issuing/controls/control-profiles/profile_id",
                    authorization,
                    ControlProfileResponse.class,
                    request,
                    null
            )).thenReturn(expectedResponse);

            final ControlProfileResponse actualResponse = client.updateControlProfileSync("profile_id", request);

            validateControlProfileResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldRemoveControlProfileSync() {
            final EmptyResponse expectedResponse = createEmptyResponse();

            when(apiClient.delete(
                    "issuing/controls/control-profiles/profile_id",
                    authorization,
                    EmptyResponse.class
            )).thenReturn(expectedResponse);

            final EmptyResponse actualResponse = client.removeControlProfileSync("profile_id");

            validateEmptyResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldAddTargetToControlProfileSync() {
            final VoidResponse expectedResponse = createVoidResponse();

            when(apiClient.post(
                    "issuing/controls/control-profiles/profile_id/add/target_id",
                    authorization,
                    VoidResponse.class,
                    null,
                    null
            )).thenReturn(expectedResponse);

            final VoidResponse actualResponse = client.addTargetToControlProfileSync("profile_id", "target_id");
            validateVoidResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldRemoveTargetFromControlProfileSync() {
            final VoidResponse expectedResponse = createVoidResponse();

            when(apiClient.post(
                    "issuing/controls/control-profiles/profile_id/remove/target_id",
                    authorization,
                    VoidResponse.class,
                    null,
                    null
            )).thenReturn(expectedResponse);

            final VoidResponse actualResponse = client.removeTargetFromControlProfileSync("profile_id", "target_id");

            validateVoidResponse(expectedResponse, actualResponse);
        }
    }

    @DisplayName("Disputes Sync")
    class DisputesSync {
        @Test
        void shouldCreateDisputeSync() {
            final CreateDisputeRequest request = createCreateDisputeRequest();
            final DisputeResponse expectedResponse = createDisputeResponse();

            when(apiClient.post(
                    "issuing/disputes",
                    authorization,
                    DisputeResponse.class,
                    request,
                    "idempotencyKey"
            )).thenReturn(expectedResponse);

            final DisputeResponse actualResponse = client.createDisputeSync(request, "idempotencyKey");

            validateDisputeResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetDisputeSync() {
            final DisputeResponse expectedResponse = createDisputeResponse();

            when(apiClient.get(
                    "issuing/disputes/dispute_id",
                    authorization,
                    DisputeResponse.class))
                    .thenReturn(expectedResponse);

            final DisputeResponse actualResponse = client.getDisputeSync("dispute_id");

            validateDisputeResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldCancelDisputeSync() {
            final VoidResponse expectedResponse = createVoidResponse();

            when(apiClient.post(
                    "issuing/disputes/dispute_id/cancel",
                    authorization,
                    VoidResponse.class,
                    null,
                    "idempotencyKey"
            )).thenReturn(expectedResponse);

            final VoidResponse actualResponse = client.cancelDisputeSync("dispute_id", "idempotencyKey");

            validateVoidResponse(expectedResponse, actualResponse);
        }

        void shouldEscalateDisputeSync() {
            final EscalateDisputeRequest request = createEscalateDisputeRequest();
            final VoidResponse expectedResponse = createVoidResponse();

            when(apiClient.post(
                    "issuing/disputes/dispute_id/escalate",
                    authorization,
                    VoidResponse.class,
                    request,
                    "idempotencyKey"
            )).thenReturn(expectedResponse);

            final VoidResponse actualResponse = client.escalateDisputeSync("dispute_id", "idempotencyKey", request);

            validateVoidResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldSubmitDisputeSync() {
            final SubmitDisputeRequest request = createSubmitDisputeRequest();
            final DisputeResponse expectedResponse = createDisputeResponse();

            when(apiClient.post(
                    "issuing/disputes/dispute_id/submit",
                    authorization,
                    DisputeResponse.class,
                    request,
                    "idempotencyKey"
            )).thenReturn(expectedResponse);

            final DisputeResponse actualResponse = client.submitDisputeSync("dispute_id", "idempotencyKey", request);

            validateDisputeResponse(expectedResponse, actualResponse);
        }
    }

    @Nested
    @DisplayName("Transactions")
    class Transactions {
        @Test
        void shouldGetListTransactions() throws ExecutionException, InterruptedException {
            final TransactionsQuery query = createTransactionsQuery();
            final TransactionsListResponse expectedResponse = createTransactionsListResponse();

            when(apiClient.queryAsync(
                    "issuing/transactions",
                    authorization,
                    query,
                    TransactionsListResponse.class
            )).thenReturn(CompletableFuture.completedFuture(expectedResponse));

            final CompletableFuture<TransactionsListResponse> future = client.getListTransactions(query);
            final TransactionsListResponse actualResponse = future.get();

            validateTransactionsListResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetSingleTransaction() throws ExecutionException, InterruptedException {
            final TransactionsSingleResponse expectedResponse = createTransactionsSingleResponse();

            when(apiClient.getAsync(
                    "issuing/transactions/transaction_id",
                    authorization,
                    TransactionsSingleResponse.class
            )).thenReturn(CompletableFuture.completedFuture(expectedResponse));

            final CompletableFuture<TransactionsSingleResponse> future = client.getSingleTransaction("transaction_id");
            final TransactionsSingleResponse actualResponse = future.get();

            validateTransactionsSingleResponse(expectedResponse, actualResponse);
        }
    }

    // Synchronous methods
    @Nested
    @DisplayName("Transactions Sync")
    class TransactionsSync {
        @Test
        void shouldGetListTransactionsSync() {
            final TransactionsQuery query = createTransactionsQuery();
            final TransactionsListResponse expectedResponse = createTransactionsListResponse();

            when(apiClient.query(
                    "issuing/transactions",
                    authorization,
                    query,
                    TransactionsListResponse.class
            )).thenReturn(expectedResponse);

            final TransactionsListResponse actualResponse = client.getListTransactionsSync(query);

            validateTransactionsListResponse(expectedResponse, actualResponse);
        }

        @Test
        void shouldGetSingleTransactionSync() {
            final TransactionsSingleResponse expectedResponse = createTransactionsSingleResponse();

            when(apiClient.get(
                    "issuing/transactions/transaction_id",
                    authorization,
                    TransactionsSingleResponse.class
            )).thenReturn(expectedResponse);

            final TransactionsSingleResponse actualResponse = client.getSingleTransactionSync("transaction_id");

            validateTransactionsSingleResponse(expectedResponse, actualResponse);
        }
    }

    // Common methods
    private CardholderRequest createCardholderRequest() {
        return mock(CardholderRequest.class);
    }

    private CardholderResponse createCardholderResponse() {
        return mock(CardholderResponse.class);
    }

    private CardholderAccessTokenRequest createCardholderAccessTokenRequest() {
        return mock(CardholderAccessTokenRequest.class);
    }

    private CardholderAccessTokenResponse createCardholderAccessTokenResponse() {
        return mock(CardholderAccessTokenResponse.class);
    }

    private CardholderUpdateRequest createCardholderUpdateRequest() {
        return mock(CardholderUpdateRequest.class);
    }

    private CardholderUpdateResponse createCardholderUpdateResponse() {
        return mock(CardholderUpdateResponse.class);
    }

    private CardholderDetailsResponse createCardholderDetailsResponse() {
        return mock(CardholderDetailsResponse.class);
    }

    private CardholderCardsResponse createCardholderCardsResponse() {
        return mock(CardholderCardsResponse.class);
    }

    private VirtualCardRequest createVirtualCardRequest() {
        return mock(VirtualCardRequest.class);
    }

    private CardResponse createCardResponse() {
        return mock(CardResponse.class);
    }

    private CardDetailsResponse createCardDetailsResponse() {
        return mock(CardDetailsResponse.class);
    }

    private PasswordThreeDSEnrollmentRequest createPasswordThreeDSEnrollmentRequest() {
        return mock(PasswordThreeDSEnrollmentRequest.class);
    }

    private ThreeDSEnrollmentResponse createThreeDSEnrollmentResponse() {
        return mock(ThreeDSEnrollmentResponse.class);
    }

    private ThreeDSUpdateRequest createThreeDSUpdateRequest() {
        return mock(ThreeDSUpdateRequest.class);
    }

    private ThreeDSUpdateResponse createThreeDSUpdateResponse() {
        return mock(ThreeDSUpdateResponse.class);
    }

    private ThreeDSEnrollmentDetailsResponse createThreeDSEnrollmentDetailsResponse() {
        return mock(ThreeDSEnrollmentDetailsResponse.class);
    }

    private VoidResponse createVoidResponse() {
        return mock(VoidResponse.class);
    }

    private CardCredentialsQuery createCardCredentialsQuery() {
        return mock(CardCredentialsQuery.class);
    }

    private CardCredentialsResponse createCardCredentialsResponse() {
        return mock(CardCredentialsResponse.class);
    }

    private RevokeCardRequest createRevokeCardRequest() {
        return mock(RevokeCardRequest.class);
    }

    private SuspendCardRequest createSuspendCardRequest() {
        return mock(SuspendCardRequest.class);
    }

    private UpdateCardRequest createUpdateCardRequest() {
        return mock(UpdateCardRequest.class);
    }

    private UpdateCardResponse createUpdateCardResponse() {
        return mock(UpdateCardResponse.class);
    }

    private RenewCardRequest createRenewCardRequest() {
        return mock(RenewCardRequest.class);
    }

    private RenewCardResponse createRenewCardResponse() {
        return mock(RenewCardResponse.class);
    }

    private ScheduleRevocationRequest createScheduleRevocationRequest() {
        return mock(ScheduleRevocationRequest.class);
    }

    private CardControlRequest createCardControlRequest() {
        return mock(CardControlRequest.class);
    }

    private CardControlResponse createCardControlResponse() {
        return mock(CardControlResponse.class);
    }

    private CardControlsQuery createCardControlsQuery() {
        return mock(CardControlsQuery.class);
    }

    private CardControlsQueryResponse createCardControlsQueryResponse() {
        return mock(CardControlsQueryResponse.class);
    }

    private UpdateCardControlRequest createUpdateCardControlRequest() {
        return mock(UpdateCardControlRequest.class);
    }

    private IdResponse createIdResponse() {
        return mock(IdResponse.class);
    }

    private CardAuthorizationRequest createCardAuthorizationRequest() {
        return mock(CardAuthorizationRequest.class);
    }

    private CardAuthorizationResponse createCardAuthorizationResponse() {
        return mock(CardAuthorizationResponse.class);
    }

    private CardAuthorizationIncrementingRequest createCardAuthorizationIncrementingRequest() {
        return mock(CardAuthorizationIncrementingRequest.class);
    }

    private CardAuthorizationIncrementingResponse createCardAuthorizationIncrementingResponse() {
        return mock(CardAuthorizationIncrementingResponse.class);
    }

    private CardAuthorizationClearingRequest createCardAuthorizationClearingRequest() {
        return mock(CardAuthorizationClearingRequest.class);
    }

    private EmptyResponse createEmptyResponse() {
        return mock(EmptyResponse.class);
    }

    private CardAuthorizationRefundsRequest createCardAuthorizationRefundsRequest() {
        return mock(CardAuthorizationRefundsRequest.class);
    }

    private CardAuthorizationReversalRequest createCardAuthorizationReversalRequest() {
        return mock(CardAuthorizationReversalRequest.class);
    }

    private CardAuthorizationReversalResponse createCardAuthorizationReversalResponse() {
        return mock(CardAuthorizationReversalResponse.class);
    }

    private CreateDisputeRequest createCreateDisputeRequest() {
        return mock(CreateDisputeRequest.class);
    }

    private DisputeResponse createDisputeResponse() {
        return mock(DisputeResponse.class);
    }

    private EscalateDisputeRequest createEscalateDisputeRequest() {
        return mock(EscalateDisputeRequest.class);
    }

    private SubmitDisputeRequest createSubmitDisputeRequest() {
        return mock(SubmitDisputeRequest.class);
    }

    private void validateCardholderResponse(CardholderResponse expected, CardholderResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardholderAccessTokenResponse(CardholderAccessTokenResponse expected, CardholderAccessTokenResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardholderUpdateResponse(CardholderUpdateResponse expected, CardholderUpdateResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardholderDetailsResponse(CardholderDetailsResponse expected, CardholderDetailsResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardholderCardsResponse(CardholderCardsResponse expected, CardholderCardsResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardResponse(CardResponse expected, CardResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardDetailsResponse(CardDetailsResponse expected, CardDetailsResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateThreeDSEnrollmentResponse(ThreeDSEnrollmentResponse expected, ThreeDSEnrollmentResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateThreeDSUpdateResponse(ThreeDSUpdateResponse expected, ThreeDSUpdateResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateThreeDSEnrollmentDetailsResponse(ThreeDSEnrollmentDetailsResponse expected, ThreeDSEnrollmentDetailsResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateVoidResponse(VoidResponse expected, VoidResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardCredentialsResponse(CardCredentialsResponse expected, CardCredentialsResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardControlResponse(CardControlResponse expected, CardControlResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardControlsQueryResponse(CardControlsQueryResponse expected, CardControlsQueryResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateIdResponse(IdResponse expected, IdResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardAuthorizationResponse(CardAuthorizationResponse expected, CardAuthorizationResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardAuthorizationIncrementingResponse(CardAuthorizationIncrementingResponse expected, CardAuthorizationIncrementingResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateEmptyResponse(EmptyResponse expected, EmptyResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateCardAuthorizationReversalResponse(CardAuthorizationReversalResponse expected, CardAuthorizationReversalResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateUpdateCardResponse(UpdateCardResponse expected, UpdateCardResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateRenewCardResponse(RenewCardResponse expected, RenewCardResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    // Control Group common methods
    private CreateControlGroupRequest createCreateControlGroupRequest() {
        return mock(CreateControlGroupRequest.class);
    }

    private ControlGroupQuery createControlGroupQuery() {
        return mock(ControlGroupQuery.class);
    }

    private ControlGroupResponse createControlGroupResponse() {
        return mock(ControlGroupResponse.class);
    }

    private ControlGroupsQueryResponse createControlGroupsQueryResponse() {
        return mock(ControlGroupsQueryResponse.class);
    }

    private void validateControlGroupResponse(ControlGroupResponse expected, ControlGroupResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateControlGroupsQueryResponse(ControlGroupsQueryResponse expected, ControlGroupsQueryResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    // Control Profile common methods
    private CreateControlProfileRequest createCreateControlProfileRequest() {
        return mock(CreateControlProfileRequest.class);
    }

    private ControlProfileQuery createControlProfileQuery() {
        return mock(ControlProfileQuery.class);
    }

    private UpdateControlProfileRequest createUpdateControlProfileRequest() {
        return mock(UpdateControlProfileRequest.class);
    }

    private ControlProfileResponse createControlProfileResponse() {
        return mock(ControlProfileResponse.class);
    }

    private ControlProfilesQueryResponse createControlProfilesQueryResponse() {
        return mock(ControlProfilesQueryResponse.class);
    }

    private void validateControlProfileResponse(ControlProfileResponse expected, ControlProfileResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateControlProfilesQueryResponse(ControlProfilesQueryResponse expected, ControlProfilesQueryResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
    
    private void validateDisputeResponse(DisputeResponse expected, DisputeResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private TransactionsQuery createTransactionsQuery() {
        return mock(TransactionsQuery.class);
    }

    private TransactionsListResponse createTransactionsListResponse() {
        return mock(TransactionsListResponse.class);
    }

    private TransactionsSingleResponse createTransactionsSingleResponse() {
        return mock(TransactionsSingleResponse.class);
    }

    private void validateTransactionsListResponse(TransactionsListResponse expected, TransactionsListResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateTransactionsSingleResponse(TransactionsSingleResponse expected, TransactionsSingleResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
