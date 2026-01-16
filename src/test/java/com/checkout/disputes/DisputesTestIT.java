package com.checkout.disputes;

import static com.checkout.CardSourceHelper.getCardSourcePaymentForDispute;
import static com.checkout.CardSourceHelper.getIndividualSender;
import static com.checkout.CardSourceHelper.getRequestCardSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;

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

class DisputesTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldQueryDisputes() {
        final DisputesQueryFilter query = createDisputesQueryFilter();
        final DisputesQueryResponse response = blocking(() -> checkoutApi.disputesClient().query(query));
        
        validateDisputesQueryResponse(query, response);
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
            
            validateDisputeDetailsResponse(disputeQueried, detailsResponse);
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

    @Test
    void shouldGetDisputeSchemeFiles() {
        final DisputesQueryFilter query = createSchemeFilesQueryFilter();
        final DisputesQueryResponse response = blocking(() -> checkoutApi.disputesClient().query(query));
        assertNotNull(response);

        if (response.getData() != null) {
            response.getData().forEach(dispute -> {
                final SchemeFileResponse schemeFileResponse = blocking(() -> checkoutApi.disputesClient().getDisputeSchemeFiles(dispute.getId()));
                validateSchemeFileResponse(schemeFileResponse);
            });
        }
    }


    /**
     * This test is disabled to avoid long waiting due the async operations that requires to perform a dispute
     * however its complete functional and can be enabled for digging purposes
     */
    //@Test
    //@Timeout(value = 3, unit = TimeUnit.MINUTES)
    void shouldTestFullDisputesWorkFlow() throws Exception {
        final PaymentResponse paymentResponse = createPaymentForDispute();
        final DisputesQueryResponse queryResponse = queryDisputesForPayment(paymentResponse.getId());

        final DisputeDetailsResponse disputeDetails = blocking(() -> checkoutApi.disputesClient()
                .getDisputeDetails(queryResponse.getData().get(0).getId()));
        validateDisputePaymentDetails(paymentResponse, disputeDetails);

        //Upload your dispute file evidence
        final FileRequest fileRequest = createFileRequest();
        final IdResponse fileResponse = blocking(() -> checkoutApi.disputesClient().uploadFile(fileRequest));
        assertNotNull(fileResponse);
        assertNotNull(fileResponse.getId());
        
        final FileDetailsResponse fileDetailsResponse = blocking(() -> checkoutApi.disputesClient().getFileDetails(fileResponse.getId()));
        validateFileDetailsResponse(fileRequest, fileDetailsResponse);

        //Provide dispute evidence
        final DisputeEvidenceRequest evidenceRequest = createEvidenceRequest(fileDetailsResponse.getId());
        blocking(() -> checkoutApi.disputesClient().putEvidence(disputeDetails.getId(), evidenceRequest));

        //Retrieve your dispute evidence details
        final DisputeEvidenceResponse evidenceResponse = blocking(() -> checkoutApi.disputesClient().getEvidence(disputeDetails.getId()));
        validateEvidenceResponse(evidenceRequest, evidenceResponse);

        //Submit your dispute evidence
        blocking(() -> checkoutApi.disputesClient().submitEvidence(disputeDetails.getId()));

        //Get compiled submitted evidence
        final DisputeCompiledSubmittedEvidenceResponse compiledSubmittedEvidenceResponse = blocking(() -> checkoutApi.disputesClient().getCompiledSubmittedEvidence(disputeDetails.getId()));
        assertNotNull(compiledSubmittedEvidenceResponse);
        assertNotNull(compiledSubmittedEvidenceResponse.getFileId());
    }

    // Synchronous methods
    @Test
    void shouldQueryDisputesSync() {
        final DisputesQueryFilter query = createDisputesQueryFilter();
        final DisputesQueryResponse response = checkoutApi.disputesClient().querySync(query);
        
        validateDisputesQueryResponse(query, response);
        if (response.getTotalCount() > 0) {
            final Dispute dispute = response.getData().get(0);
            final DisputesQueryFilter query2 = DisputesQueryFilter.builder().id(dispute.getId()).build();
            final DisputesQueryResponse responseDsp = checkoutApi.disputesClient().querySync(query2);
            assertNotNull(responseDsp);
            assertEquals(1, responseDsp.getTotalCount());
        }
    }

    @Test
    void shouldGetDisputeDetailsSync() {
        final DisputesQueryResponse queryResponse = checkoutApi.disputesClient().querySync(DisputesQueryFilter.builder().build());
        assertNotNull(queryResponse);
        if (queryResponse.getTotalCount() > 0) {
            final Dispute disputeQueried = queryResponse.getData().get(0);
            final DisputeDetailsResponse detailsResponse = checkoutApi.disputesClient().getDisputeDetailsSync(disputeQueried.getId());
            
            validateDisputeDetailsResponse(disputeQueried, detailsResponse);
        }
    }

    @Test
    void shouldFailOnAcceptDisputeAlreadyAcceptedSync() {
        final DisputesQueryResponse queryResponse = checkoutApi.disputesClient()
                .querySync(DisputesQueryFilter.builder().statuses(DisputeStatus.ACCEPTED.toString()).build());
        assertNotNull(queryResponse);
        if (queryResponse.getTotalCount() > 0) {
            final Dispute dispute = queryResponse.getData().get(0);
            try {
                checkoutApi.disputesClient().acceptSync(dispute.getId());
                fail();
            } catch (final Exception e) {
                assertTrue(e instanceof CheckoutApiException);
                assertTrue(e.getMessage().contains("dispute_already_accepted"));
            }
        }
    }

    @Test
    void shouldGetDisputeSchemeFilesSync() {
        final DisputesQueryFilter query = createSchemeFilesQueryFilter();
        final DisputesQueryResponse response = checkoutApi.disputesClient().querySync(query);
        assertNotNull(response);

        if (response.getData() != null) {
            response.getData().forEach(dispute -> {
                final SchemeFileResponse schemeFileResponse = checkoutApi.disputesClient().getDisputeSchemeFilesSync(dispute.getId());
                validateSchemeFileResponse(schemeFileResponse);
            });
        }
    }

    @Test
    void shouldCreateAndUploadFileSync() throws Exception {
        final PaymentResponse paymentResponse = createPaymentForDispute();
        final DisputesQueryResponse queryResponse = queryDisputesForPayment(paymentResponse.getId());
        
        if (queryResponse.getTotalCount() == 0) return;
        
        final DisputeDetailsResponse disputeDetails = checkoutApi.disputesClient()
                .getDisputeDetailsSync(queryResponse.getData().get(0).getId());
        validateDisputePaymentDetails(paymentResponse, disputeDetails);

        //Upload your dispute file evidence
        final FileRequest fileRequest = createFileRequest();
        final IdResponse fileResponse = checkoutApi.disputesClient().uploadFileSync(fileRequest);
        assertNotNull(fileResponse);
        assertNotNull(fileResponse.getId());
        
        final FileDetailsResponse fileDetailsResponse = checkoutApi.disputesClient().getFileDetailsSync(fileResponse.getId());
        validateFileDetailsResponse(fileRequest, fileDetailsResponse);

        //Provide dispute evidence
        final DisputeEvidenceRequest evidenceRequest = createEvidenceRequest(fileDetailsResponse.getId());
        checkoutApi.disputesClient().putEvidenceSync(disputeDetails.getId(), evidenceRequest);

        //Retrieve your dispute evidence details
        final DisputeEvidenceResponse evidenceResponse = checkoutApi.disputesClient().getEvidenceSync(disputeDetails.getId());
        validateEvidenceResponse(evidenceRequest, evidenceResponse);

        //Submit your dispute evidence
        checkoutApi.disputesClient().submitEvidenceSync(disputeDetails.getId());

        //Get compiled submitted evidence
        final DisputeCompiledSubmittedEvidenceResponse compiledSubmittedEvidenceResponse = checkoutApi.disputesClient().getCompiledSubmittedEvidenceSync(disputeDetails.getId());
        assertNotNull(compiledSubmittedEvidenceResponse);
        assertNotNull(compiledSubmittedEvidenceResponse.getFileId());
    }

    // Common methods
    private DisputesQueryFilter createDisputesQueryFilter() {
        return DisputesQueryFilter
                .builder()
                .to(Instant.now())
                .from(LocalDateTime.now().minusMonths(6).toInstant(ZoneOffset.UTC))
                .limit(100)
                .thisChannelOnly(true)
                .build();
    }

    private DisputesQueryFilter createSchemeFilesQueryFilter() {
        return DisputesQueryFilter
                .builder()
                .to(Instant.now())
                .from(LocalDateTime.now().minusMonths(1).toInstant(ZoneOffset.UTC))
                .limit(5)
                .thisChannelOnly(true)
                .build();
    }

    private PaymentResponse createPaymentForDispute() {
        final RequestCardSource cardSource = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();
        final PaymentRequest paymentRequest = getCardSourcePaymentForDispute(cardSource, sender, false);
        return blocking(() -> checkoutApi.paymentsClient().requestPayment(paymentRequest));
    }

    private DisputesQueryResponse queryDisputesForPayment(String paymentId) {
        return blocking(() -> checkoutApi.disputesClient()
                .query(DisputesQueryFilter.builder().paymentId(paymentId).build()));
    }

    private FileRequest createFileRequest() throws Exception {
        final URL resource = getClass().getClassLoader().getResource("checkout.pdf");
        final File file = new File(resource.toURI());
        return FileRequest.builder()
                .file(file)
                .contentType(ContentType.create("application/pdf"))
                .purpose(FilePurpose.DISPUTE_EVIDENCE)
                .build();
    }

    private DisputeEvidenceRequest createEvidenceRequest(String fileId) {
        return DisputeEvidenceRequest.builder()
                .proofOfDeliveryOrServiceFile(fileId)
                .proofOfDeliveryOrServiceText("proof of delivery or service text")
                .invoiceOrReceiptFile(fileId)
                .invoiceOrReceiptText("Copy of the invoice")
                .invoiceShowingDistinctTransactionsFile(fileId)
                .invoiceShowingDistinctTransactionsText("Copy of invoice #1244 showing two transactions")
                .customerCommunicationFile(fileId)
                .customerCommunicationText("Copy of an email exchange with the cardholder")
                .refundOrCancellationPolicyFile(fileId)
                .refundOrCancellationPolicyText("Copy of the refund policy")
                .recurringTransactionAgreementFile(fileId)
                .recurringTransactionAgreementText("Copy of the recurring transaction agreement")
                .additionalEvidenceFile(fileId)
                .additionalEvidenceText("Scanned document")
                .proofOfDeliveryOrServiceDateFile(fileId)
                .proofOfDeliveryOrServiceDateText("Copy of the customer receipt showing the merchandise was delivered on 2018-12-20")
                .build();
    }

    private void validateDisputesQueryResponse(DisputesQueryFilter query, DisputesQueryResponse response) {
        assertNotNull(response);
        assertEquals(query.getLimit(), response.getLimit());
        assertEquals(query.getThisChannelOnly(), response.isThisChannelOnly());
        assertEquals(query.getTo().truncatedTo(ChronoUnit.SECONDS), response.getTo());
        assertEquals(query.getFrom().truncatedTo(ChronoUnit.SECONDS), response.getFrom());
    }

    private void validateDisputeDetailsResponse(Dispute disputeQueried, DisputeDetailsResponse detailsResponse) {
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

    private void validateSchemeFileResponse(SchemeFileResponse schemeFileResponse) {
        assertNotNull(schemeFileResponse);
        assertNotNull(schemeFileResponse.getId());
        assertNotNull(schemeFileResponse.getFiles());
    }

    private void validateDisputePaymentDetails(PaymentResponse paymentResponse, DisputeDetailsResponse disputeDetails) {
        assertEquals(paymentResponse.getId(), disputeDetails.getPayment().getId());
        assertEquals(paymentResponse.getAmount(), disputeDetails.getPayment().getAmount());
        assertNotNull(disputeDetails.getRelevantEvidence());
    }

    private void validateFileDetailsResponse(FileRequest fileRequest, FileDetailsResponse fileDetailsResponse) {
        assertNotNull(fileDetailsResponse);
        assertEquals(fileRequest.getFile().getName(), fileDetailsResponse.getFilename());
        assertEquals(fileRequest.getPurpose().getPurpose(), fileDetailsResponse.getPurpose());
    }

    private void validateEvidenceResponse(DisputeEvidenceRequest evidenceRequest, DisputeEvidenceResponse evidenceResponse) {
        assertNotNull(evidenceResponse);
        assertEquals(evidenceRequest.getProofOfDeliveryOrServiceFile(), evidenceResponse.getProofOfDeliveryOrServiceFile());
        assertEquals(evidenceRequest.getProofOfDeliveryOrServiceText(), evidenceResponse.getProofOfDeliveryOrServiceText());
        assertEquals(evidenceRequest.getProofOfDeliveryOrServiceDateText(), evidenceResponse.getProofOfDeliveryOrServiceDateText());
    }

}
