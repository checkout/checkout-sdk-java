package com.checkout.disputes;

import com.checkout.CheckoutApiException;
import com.checkout.common.FileDetailsResponse;
import com.checkout.common.FilePurpose;
import com.checkout.common.FileRequest;
import com.checkout.common.IdResponse;
import com.checkout.payments.AbstractPaymentsTestIT;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.sender.PaymentIndividualSender;
import org.apache.http.entity.ContentType;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static com.checkout.CardSourceHelper.getCardSourcePaymentForDispute;
import static com.checkout.CardSourceHelper.getIndividualSender;
import static com.checkout.CardSourceHelper.getRequestCardSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class DisputesTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldQueryDisputes() {
        final DisputesQueryFilter query = DisputesQueryFilter
                .builder()
                .to(Instant.now())
                .from(LocalDateTime.now().minusMonths(6).toInstant(ZoneOffset.UTC))
                .limit(100)
                .thisChannelOnly(true)
                .build();
        final DisputesQueryResponse response = blocking(() -> checkoutApi.disputesClient().query(query));
        assertNotNull(response);
        assertEquals(query.getLimit(), response.getLimit());
        assertEquals(query.getThisChannelOnly(), response.isThisChannelOnly());
        assertEquals(query.getTo().truncatedTo(ChronoUnit.SECONDS), response.getTo());
        assertEquals(query.getFrom().truncatedTo(ChronoUnit.SECONDS), response.getFrom());
        if (response.getTotalCount() > 0) {
            final Dispute dispute = response.getData().get(0);
            final DisputesQueryFilter query2 = DisputesQueryFilter.builder().id(dispute.getId()).build();
            final DisputesQueryResponse responseDsp = blocking(() -> checkoutApi.disputesClient().query(query2));
            assertNotNull(responseDsp);
            assertEquals(1, responseDsp.getTotalCount());
        }
    }

    @Test
    void shouldGetDisputeDetails() {
        final DisputesQueryResponse queryResponse = blocking(() -> checkoutApi.disputesClient().query(DisputesQueryFilter.builder().build()));
        assertNotNull(queryResponse);
        if (queryResponse.getTotalCount() > 0) {
            final Dispute disputeQueried = queryResponse.getData().get(0);
            final DisputeDetailsResponse detailsResponse = blocking(() -> checkoutApi.disputesClient().getDisputeDetails(disputeQueried.getId()));
            assertNotNull(detailsResponse);
            assertEquals(disputeQueried.getId(), detailsResponse.getId());
            assertNotNull(detailsResponse.getStatus());
            assertEquals(disputeQueried.getCategory(), detailsResponse.getCategory());
            assertEquals(disputeQueried.getAmount(), detailsResponse.getAmount());
            assertEquals(disputeQueried.getCurrency(), detailsResponse.getCurrency());
            assertEquals(disputeQueried.getReasonCode(), detailsResponse.getReasonCode());
            assertEquals(disputeQueried.getStatus(), detailsResponse.getStatus());
            assertEquals(disputeQueried.getReceivedOn(), detailsResponse.getReceivedOn());
            if (disputeQueried.getPaymentId() != null) {
                assertNotNull(detailsResponse.getPayment());
                assertEquals(disputeQueried.getPaymentId(), detailsResponse.getPayment().getId());
                assertEquals(disputeQueried.getPaymentActionId(), detailsResponse.getPayment().getActionId());
                assertEquals(disputeQueried.getPaymentMethod(), detailsResponse.getPayment().getMethod());
                assertEquals(disputeQueried.getPaymentArn(), detailsResponse.getPayment().getArn());
            }
        }
    }

    @Test
    void shouldFailOnAcceptDisputeAlreadyAccepted() {
        final DisputesQueryResponse queryResponse = blocking(() -> checkoutApi.disputesClient()
                .query(DisputesQueryFilter.builder().statuses(DisputeStatus.ACCEPTED.toString()).build()));
        assertNotNull(queryResponse);
        if (queryResponse.getTotalCount() > 0) {
            final Dispute dispute = queryResponse.getData().get(0);
            try {
                checkoutApi.disputesClient().accept(dispute.getId()).get();
                fail();
            } catch (final Exception e) {
                assertTrue(e.getCause() instanceof CheckoutApiException);
                assertTrue(e.getMessage().contains("dispute_already_accepted"));
            }
        }
    }

    /**
     * This test is disabled to avoid long waiting due the async operations that requires to perform a dispute
     * however its complete functional and can be enabled for digging purposes
     */
    //@Test
    //@Timeout(value = 3, unit = TimeUnit.MINUTES)
    void shouldTestFullDisputesWorkFlow() throws Exception {
        //Create a payment who triggers a dispute
        final RequestCardSource source = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();
        final PaymentRequest request = getCardSourcePaymentForDispute(source, sender, false);

        // payment
        final PaymentResponse paymentResponse = makeCardPayment(request);
        assertNotNull(paymentResponse.getLink("capture"));

        // capture
        capturePayment(paymentResponse.getId());

        //Query for dispute
        final DisputesQueryFilter query = DisputesQueryFilter.builder()
                .paymentId(paymentResponse.getId())
                .statuses("evidence_required")
                .build();
        final DisputesQueryResponse queryResponse = blocking(() -> checkoutApi.disputesClient().query(query), IsNull.notNullValue(DisputesQueryResponse.class));

        //Get dispute details
        final DisputeDetailsResponse disputeDetails = blocking(() -> checkoutApi.disputesClient()
                .getDisputeDetails(queryResponse.getData().get(0).getId()));
        assertEquals(paymentResponse.getId(), disputeDetails.getPayment().getId());
        assertEquals(paymentResponse.getAmount(), disputeDetails.getPayment().getAmount());
        assertNotNull(disputeDetails.getRelevantEvidence());

        //Upload your dispute file evidence
        final URL resource = getClass().getClassLoader().getResource("checkout.pdf");
        final File file = new File(resource.toURI());
        final FileRequest fileRequest = FileRequest.builder()
                .file(file)
                .contentType(ContentType.create("application/pdf"))
                .purpose(FilePurpose.DISPUTE_EVIDENCE)
                .build();
        final IdResponse fileResponse = blocking(() -> checkoutApi.disputesClient().uploadFile(fileRequest));
        assertNotNull(fileResponse);
        assertNotNull(fileResponse.getId());
        final FileDetailsResponse fileDetailsResponse = blocking(() -> checkoutApi.disputesClient().getFileDetails(fileResponse.getId()));
        assertNotNull(fileDetailsResponse);
        assertEquals(fileRequest.getFile().getName(), fileDetailsResponse.getFilename());
        assertEquals(fileRequest.getPurpose().getPurpose(), fileDetailsResponse.getPurpose());

        //Provide dispute evidence
        final DisputeEvidenceRequest evidenceRequest = DisputeEvidenceRequest.builder()
                .proofOfDeliveryOrServiceFile(fileDetailsResponse.getId())
                .proofOfDeliveryOrServiceText("proof of delivery or service text")
                .invoiceOrReceiptFile(fileDetailsResponse.getId())
                .invoiceOrReceiptText("Copy of the invoice")
                .invoiceShowingDistinctTransactionsFile(fileDetailsResponse.getId())
                .invoiceShowingDistinctTransactionsText("Copy of invoice #1244 showing two transactions")
                .customerCommunicationFile(fileDetailsResponse.getId())
                .customerCommunicationText("Copy of an email exchange with the cardholder")
                .refundOrCancellationPolicyFile(fileDetailsResponse.getId())
                .refundOrCancellationPolicyText("Copy of the refund policy")
                .recurringTransactionAgreementFile(fileDetailsResponse.getId())
                .recurringTransactionAgreementText("Copy of the recurring transaction agreement")
                .additionalEvidenceFile(fileDetailsResponse.getId())
                .additionalEvidenceText("Scanned document")
                .proofOfDeliveryOrServiceDateFile(fileDetailsResponse.getId())
                .proofOfDeliveryOrServiceDateText("Copy of the customer receipt showing the merchandise was delivered on 2018-12-20")
                .build();
        blocking(() -> checkoutApi.disputesClient().putEvidence(disputeDetails.getId(), evidenceRequest));

        //Retrieve your dispute evidence details
        final DisputeEvidenceResponse evidenceResponse = blocking(() -> checkoutApi.disputesClient().getEvidence(disputeDetails.getId()));
        assertNotNull(evidenceResponse);
        assertEquals(evidenceRequest.getProofOfDeliveryOrServiceFile(), evidenceResponse.getProofOfDeliveryOrServiceFile());
        assertEquals(evidenceRequest.getProofOfDeliveryOrServiceText(), evidenceResponse.getProofOfDeliveryOrServiceText());
        assertEquals(evidenceRequest.getProofOfDeliveryOrServiceDateText(), evidenceResponse.getProofOfDeliveryOrServiceDateText());

        //Submit your dispute evidence
        blocking(() -> checkoutApi.disputesClient().submitEvidence(disputeDetails.getId()));
    }


}
