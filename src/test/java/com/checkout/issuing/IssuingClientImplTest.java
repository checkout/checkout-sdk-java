package com.checkout.issuing;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.IdResponse;
import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.cardholders.CardholderDetailsResponse;
import com.checkout.issuing.cardholders.CardholderRequest;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cards.requests.create.VirtualCardRequest;
import com.checkout.issuing.cards.requests.credentials.CardCredentialsQuery;
import com.checkout.issuing.cards.requests.enrollment.PasswordThreeDSEnrollmentRequest;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
            final CardholderRequest request = mock(CardholderRequest.class);
            final CardholderResponse response = mock(CardholderResponse.class);

            when(apiClient.postAsync(
                    "issuing/cardholders",
                    authorization,
                    CardholderResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderResponse> future = client.createCardholder(request);

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldGetCardholderDetails() throws ExecutionException, InterruptedException {
            final CardholderDetailsResponse response = mock(CardholderDetailsResponse.class);

            when(apiClient.getAsync(
                    "issuing/cardholders/cardholder_id",
                    authorization,
                    CardholderDetailsResponse.class))
            .thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderDetailsResponse> future = client.getCardholder("cardholder_id");

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldGetCardholderCards() throws ExecutionException, InterruptedException {
            final CardholderCardsResponse response = mock(CardholderCardsResponse.class);

            when(apiClient.getAsync(
                    "issuing/cardholders/cardholder_id/cards",
                    authorization,
                    CardholderCardsResponse.class))
                    .thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderCardsResponse> future = client.getCardholderCards("cardholder_id");

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }
    }

    @Nested
    @DisplayName("Cards")
    class Cards {
        @Test
        void shouldCreateCard() throws ExecutionException, InterruptedException {
            final VirtualCardRequest request = mock(VirtualCardRequest.class);
            final CardResponse response = mock(CardResponse.class);

            when(apiClient.postAsync(
                    "issuing/cards",
                    authorization,
                    CardResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardResponse> future = client.createCard(request);

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldGetCardDetails() throws ExecutionException, InterruptedException {
            final CardDetailsResponse response = mock(CardDetailsResponse.class);

            when(apiClient.getAsync(
                    "issuing/cards/card_id",
                    authorization,
                    CardDetailsResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardDetailsResponse> future = client.getCardDetails("card_id");

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldEnrollCardIntoThreeDS() throws ExecutionException, InterruptedException {
            final PasswordThreeDSEnrollmentRequest request = mock(PasswordThreeDSEnrollmentRequest.class);
            final ThreeDSEnrollmentResponse response = mock(ThreeDSEnrollmentResponse.class);

            when(apiClient.postAsync(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSEnrollmentResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ThreeDSEnrollmentResponse> future = client.enrollThreeDS("card_id", request);

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldUpdateThreeDSEnrollment() throws ExecutionException, InterruptedException {
            final ThreeDSUpdateRequest request = mock(ThreeDSUpdateRequest.class);
            final ThreeDSUpdateResponse response = mock(ThreeDSUpdateResponse.class);

            when(apiClient.patchAsync(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSUpdateResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ThreeDSUpdateResponse> future = client.updateThreeDS("card_id", request);

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldGetThreeDSDetails() throws ExecutionException, InterruptedException {
            final ThreeDSEnrollmentDetailsResponse response = mock(ThreeDSEnrollmentDetailsResponse.class);

            when(apiClient.getAsync(
                    "issuing/cards/card_id/3ds-enrollment",
                    authorization,
                    ThreeDSEnrollmentDetailsResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<ThreeDSEnrollmentDetailsResponse> future = client.getCardThreeDSDetails("card_id");

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldActivateCard() throws ExecutionException, InterruptedException {
            final VoidResponse response = mock(VoidResponse.class);

            when(apiClient.postAsync(
                    "issuing/cards/card_id/activate",
                    authorization,
                    VoidResponse.class,
                    null,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.activateCard("card_id");

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldGetCardCredentials() throws ExecutionException, InterruptedException {
            final CardCredentialsQuery query = mock(CardCredentialsQuery.class);
            final CardCredentialsResponse response = mock(CardCredentialsResponse.class);

            when(apiClient.queryAsync(
                    "issuing/cards/card_id/credentials",
                    authorization,
                    query,
                    CardCredentialsResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardCredentialsResponse> future = client.getCardCredentials("card_id", query);

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldRevokeCard() throws ExecutionException, InterruptedException {
            final RevokeCardRequest request = mock(RevokeCardRequest.class);
            final VoidResponse response = mock(VoidResponse.class);

            when(apiClient.postAsync(
                    "issuing/cards/card_id/revoke",
                    authorization,
                    VoidResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.revokeCard("card_id", request);

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldSuspendCard() throws ExecutionException, InterruptedException {
            final SuspendCardRequest request = mock(SuspendCardRequest.class);
            final VoidResponse response = mock(VoidResponse.class);

            when(apiClient.postAsync(
                    "issuing/cards/card_id/suspend",
                    authorization,
                    VoidResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<VoidResponse> future = client.suspendCard("card_id", request);

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }
    }

    @Nested
    @DisplayName("Controls")
    class Controls {
        @Test
        void shouldCreateControl() throws ExecutionException, InterruptedException {
            final CardControlRequest request = mock(CardControlRequest.class);
            final CardControlResponse response = mock(CardControlResponse.class);

            when(apiClient.postAsync(
                    "issuing/controls",
                    authorization,
                    CardControlResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardControlResponse> future = client.createControl(request);

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldGetCardControls() throws ExecutionException, InterruptedException {
            final CardControlsQuery query = mock(CardControlsQuery.class);
            final CardControlsQueryResponse response = mock(CardControlsQueryResponse.class);

            when(apiClient.queryAsync(
                    "issuing/controls",
                    authorization,
                    query,
                    CardControlsQueryResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardControlsQueryResponse> future = client.getCardControls(query);

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldGetCardControlDetails() throws ExecutionException, InterruptedException {
            final CardControlResponse response = mock(CardControlResponse.class);

            when(apiClient.getAsync(
                    "issuing/controls/control_id",
                    authorization,
                    CardControlResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardControlResponse> future = client.getCardControlDetails("control_id");

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldUpdateCardControl() throws ExecutionException, InterruptedException {
            final UpdateCardControlRequest request = mock(UpdateCardControlRequest.class);
            final CardControlResponse response = mock(CardControlResponse.class);

            when(apiClient.putAsync(
                    "issuing/controls/control_id",
                    authorization,
                    CardControlResponse.class,
                    request
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardControlResponse> future = client.updateCardControl("control_id", request);

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldRemoveCardControlDetails() throws ExecutionException, InterruptedException {
            final IdResponse response = mock(IdResponse.class);

            when(apiClient.deleteAsync(
                    "issuing/controls/control_id",
                    authorization,
                    IdResponse.class
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<IdResponse> future = client.removeCardControl("control_id");

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }
    }

    @Nested
    @DisplayName("Testing")
    class Testing {
        @Test
        void shouldSimulateAuthorization() throws ExecutionException, InterruptedException {
            final CardAuthorizationRequest request = mock(CardAuthorizationRequest.class);
            final CardAuthorizationResponse response = mock(CardAuthorizationResponse.class);

            when(apiClient.postAsync(
                    "issuing/simulate/authorizations",
                    authorization,
                    CardAuthorizationResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardAuthorizationResponse> future = client.simulateAuthorization(request);

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }
    }
}
