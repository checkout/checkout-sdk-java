package com.checkout.issuing;

import com.checkout.payments.VoidResponse;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cards.responses.CardResponse;
import com.checkout.issuing.disputes.entities.DisputeEvidence;
import com.checkout.issuing.disputes.entities.DisputeFileEvidence;
import com.checkout.issuing.disputes.entities.IssuingDisputeStatus;
import com.checkout.issuing.disputes.requests.CreateDisputeRequest;
import com.checkout.issuing.disputes.requests.EscalateDisputeRequest;
import com.checkout.issuing.disputes.requests.SubmitDisputeRequest;
import com.checkout.issuing.disputes.responses.DisputeResponse;
import com.checkout.issuing.testing.requests.CardAuthorizationRequest;
import com.checkout.issuing.testing.requests.CardSimulation;
import com.checkout.issuing.testing.requests.TransactionMerchant;
import com.checkout.issuing.testing.requests.TransactionSimulation;
import com.checkout.issuing.testing.requests.TransactionType;
import com.checkout.issuing.testing.responses.CardAuthorizationResponse;
import com.checkout.common.Currency;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("Avoid creating disputes all the time")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IssuingDisputesTestIT extends BaseIssuingTestIT {

    private CardResponse card;
    private CardAuthorizationResponse transaction;
    private DisputeResponse dispute;

    @BeforeAll
    void setUp() {
        final CardholderResponse cardholder = createCardholder();
        card = createCard(cardholder.getId(), true);
        transaction = createTransaction();
        dispute = createInitialDispute();
    }

    @Test
    void shouldCreateDispute() {
        validateDisputeResponse(dispute);
    }

    @Test
    void shouldGetDispute() {
        final DisputeResponse response = blocking(() ->
                issuingApi.issuingClient().getDispute(dispute.getId()));

        validateDisputeResponse(response);
    }

    @Test
    void shouldCancelDispute() {
        final DisputeResponse newDispute = createInitialDispute();
        final String idempotencyKey = UUID.randomUUID().toString();

        final VoidResponse response = blocking(() ->
                issuingApi.issuingClient().cancelDispute(newDispute.getId(), idempotencyKey));

        validateVoidResponse(response);
    }

    @Test
    void shouldEscalateDispute() {
        final DisputeResponse newDispute = createInitialDispute();
        final EscalateDisputeRequest request = createEscalateDisputeRequest();
        final String idempotencyKey = UUID.randomUUID().toString();

        final VoidResponse response = blocking(() ->
                issuingApi.issuingClient().escalateDispute(newDispute.getId(), idempotencyKey, request));

        validateVoidResponse(response);
    }

    @Test
    void shouldSubmitDispute() {
        final DisputeResponse newDispute = createInitialDispute();
        final SubmitDisputeRequest request = createSubmitDisputeRequest();
        final String idempotencyKey = UUID.randomUUID().toString();

        final DisputeResponse response = blocking(() ->
                issuingApi.issuingClient().submitDispute(newDispute.getId(), idempotencyKey, request));

        validateDisputeResponse(response);
    }

    // Synchronous methods
    @Test
    void shouldCreateDisputeSync() {
        final CreateDisputeRequest request = createCreateDisputeRequest();
        final String idempotencyKey = UUID.randomUUID().toString();

        final DisputeResponse response = 
                issuingApi.issuingClient().createDisputeSync(request, idempotencyKey);

        validateDisputeResponse(response);
    }

    @Test
    void shouldGetDisputeSync() {
        final DisputeResponse response = 
                issuingApi.issuingClient().getDisputeSync(dispute.getId());

        validateDisputeResponse(response);
    }

    @Test
    void shouldCancelDisputeSync() {
        final DisputeResponse newDispute = createInitialDispute();
        final String idempotencyKey = UUID.randomUUID().toString();

        final VoidResponse response = 
                issuingApi.issuingClient().cancelDisputeSync(newDispute.getId(), idempotencyKey);

        validateVoidResponse(response);
    }

    @Test
    void shouldEscalateDisputeSync() {
        final DisputeResponse newDispute = createInitialDispute();
        final EscalateDisputeRequest request = createEscalateDisputeRequest();
        final String idempotencyKey = UUID.randomUUID().toString();

        final VoidResponse response = 
                issuingApi.issuingClient().escalateDisputeSync(newDispute.getId(), idempotencyKey, request);

        validateVoidResponse(response);
    }

    @Test
    void shouldSubmitDisputeSync() {
        final DisputeResponse newDispute = createInitialDispute();
        final SubmitDisputeRequest request = createSubmitDisputeRequest();
        final String idempotencyKey = UUID.randomUUID().toString();

        final DisputeResponse response = 
                issuingApi.issuingClient().submitDisputeSync(newDispute.getId(), idempotencyKey, request);

        validateDisputeResponse(response);
    }

    // Common methods
    private CardAuthorizationResponse createTransaction() {
        final CardAuthorizationRequest authorizationRequest = CardAuthorizationRequest.builder()
                .card(CardSimulation.builder()
                        .id(card.getId())
                        .expiryMonth(card.getExpiryMonth())
                        .expiryYear(card.getExpiryYear())
                        .build())
                .transaction(TransactionSimulation.builder()
                        .type(TransactionType.PURCHASE)
                        .amount(10000)
                        .currency(Currency.USD)
                        .merchant(TransactionMerchant.builder()
                                .categoryCode("5411")
                                .build())
                        .build())
                .build();

        return blocking(() -> issuingApi.issuingClient().simulateAuthorization(authorizationRequest));
    }

    private DisputeResponse createInitialDispute() {
        final CreateDisputeRequest request = createCreateDisputeRequest();
        final String idempotencyKey = UUID.randomUUID().toString();

        return blocking(() -> issuingApi.issuingClient().createDispute(request, idempotencyKey));
    }

    private CreateDisputeRequest createCreateDisputeRequest() {
        return CreateDisputeRequest.builder()
                .transactionId(transaction.getId())
                .reason("fraudulent")
                .evidence(Arrays.asList(
                        DisputeEvidence.builder()
                                .name("evidence.pdf")
                                .content("base64encodedcontent")
                                .description("Evidence showing fraudulent transaction")
                                .build()
                ))
                .build();
    }

    private EscalateDisputeRequest createEscalateDisputeRequest() {
        return EscalateDisputeRequest.builder()
                .justification("Additional evidence supports our case")
                .additionalEvidence(Arrays.asList(
                        DisputeEvidence.builder()
                                .name("additional_evidence.pdf")
                                .content("base64additionalcontent")
                                .description("Additional evidence for escalation")
                                .build()
                ))
                .build();
    }

    private SubmitDisputeRequest createSubmitDisputeRequest() {
        return SubmitDisputeRequest.builder()
                .evidence(Arrays.asList(
                        DisputeEvidence.builder()
                                .name("final_evidence.pdf")
                                .content("base64finalcontent")
                                .description("Final evidence for submission")
                                .build()
                ))
                .build();
    }

    private void validateDisputeResponse(DisputeResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals("fraudulent", response.getReason());
        assertEquals(IssuingDisputeStatus.CREATED, response.getStatus());
    }

    private void validateVoidResponse(VoidResponse response) {
        assertNotNull(response);
        assertEquals(202, response.getHttpStatusCode());
    }
}