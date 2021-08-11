package com.checkout.disputes;

import com.checkout.CheckoutValidationException;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Currency;
import com.checkout.common.FileDetailsResponse;
import com.checkout.common.FilePurpose;
import com.checkout.common.FileRequest;
import com.checkout.common.IdResponse;
import com.checkout.payments.CaptureRequest;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.PaymentResponse;
import com.checkout.payments.TokenSource;
import com.checkout.tokens.CardTokenRequest;
import com.checkout.tokens.CardTokenResponse;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DisputesTestIT extends SandboxTestFixture {

    public DisputesTestIT() {
        super(PlatformType.CLASSIC);
    }

    @Test
    public void shouldQueryDisputes() {
        DisputesQueryFilter query = DisputesQueryFilter.builder().limit(250).build();
        DisputesQueryResponse response = blocking(getApi().disputesClient().query(query));
        assertNotNull(response);
        assertEquals(query.getLimit(), response.getLimit());
        if (response.getTotalCount() > 0) {
            final Dispute dispute = response.getData().get(0);
            query = DisputesQueryFilter.builder().id(dispute.getId()).build();
            response = blocking(getApi().disputesClient().query(query));
            assertNotNull(response);
            assertEquals(1L, response.getTotalCount().longValue());
            assertEquals(query.getId(), response.getData().get(0).getId());
        }
    }

    @Test
    public void shouldGetDisputeDetails() {
        DisputesQueryResponse queryResponse = blocking(getApi().disputesClient().query(DisputesQueryFilter.builder().build()));
        assertNotNull(queryResponse);
        if (queryResponse.getTotalCount() > 0) {
            Dispute disputeQueried = queryResponse.getData().get(0);
            DisputeDetailsResponse detailsResponse = blocking(getApi().disputesClient().getDisputeDetails(disputeQueried.getId()));
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
    public void shouldFailOnAcceptDisputeAlreadyAccepted() {
        DisputesQueryResponse queryResponse = blocking(getApi().disputesClient().query(DisputesQueryFilter.builder()
                .statuses(DisputeStatus.ACCEPTED.toString()).build()));
        assertNotNull(queryResponse);
        if (queryResponse.getTotalCount() > 0) {
            Dispute dispute = queryResponse.getData().get(0);
            try {
                getApi().disputesClient().accept(dispute.getId()).get();
                fail();
            } catch (Exception ex) {
                assertTrue(ex.getCause() instanceof CheckoutValidationException);
                assertTrue(ex.getMessage().contains("dispute_already_accepted"));
            }
        }
    }

    /**
     * This test is disabled to avoid long waiting due the async operations that requires to perform a dispute
     * however it complete functional and can be enabled for confirmation purposes
     */
    //@Test(timeout = 300000L)
    public void shouldTestFullDisputesWorkFlow() throws Exception {
        //Create a payment who triggers a dispute
        CardTokenRequest cardTokenRequest = TestHelper.createCardTokenRequest();
        CardTokenResponse cardTokenResponse = blocking(getApi().tokensClient().requestAsync(cardTokenRequest));
        final PaymentRequest<TokenSource> paymentRequest = PaymentRequest.fromSource(
                new TokenSource(cardTokenResponse.getToken()), Currency.GBP, 1040L);
        paymentRequest.setCapture(true);
        final PaymentResponse paymentResponse = blocking(getApi().paymentsClient().requestAsync(paymentRequest));
        Assert.assertTrue(paymentResponse.getPayment().canCapture());
        final CaptureRequest captureRequest = new CaptureRequest();
        captureRequest.setReference(UUID.randomUUID().toString());
        blocking(getApi().paymentsClient().captureAsync(paymentResponse.getPayment().getId(), captureRequest));
        //Query for dispute
        DisputesQueryResponse queryResponse = null;
        DisputesQueryFilter query = DisputesQueryFilter.builder()
                .paymentId(paymentResponse.getPayment().getId())
                .statuses(DisputeStatus.EVIDENCE_REQUIRED.getStatus())
                .build();
        while (queryResponse == null || queryResponse.getTotalCount() == 0) {
            TimeUnit.SECONDS.sleep(20);
            queryResponse = blocking(getApi().disputesClient().query(query));
        }
        //Get dispute details
        DisputeDetailsResponse disputeDetails = blocking(getApi().disputesClient()
                .getDisputeDetails(queryResponse.getData().get(0).getId()));
        assertEquals(paymentResponse.getPayment().getId(), disputeDetails.getPayment().getId());
        assertThat(paymentResponse.getPayment().getAmount(), equalTo(disputeDetails.getPayment().getAmount()));
        assertNotNull(disputeDetails.getRelevantEvidence());
        //Upload your dispute file evidence
        URL resource = getClass().getClassLoader().getResource("checkout.pdf");
        File file = new File(resource.toURI());
        FileRequest fileRequest = FileRequest.builder()
                .file(file)
                .contentType(ContentType.create("application/pdf"))
                .purpose(FilePurpose.DISPUTE_EVIDENCE)
                .build();
        IdResponse fileResponse = blocking(getApi().disputesClient().uploadFile(fileRequest));
        assertNotNull(fileResponse);
        assertNotNull(fileResponse.getId());
        FileDetailsResponse fileDetailsResponse = blocking(getApi().disputesClient().getFileDetails(fileResponse.getId()));
        assertNotNull(fileDetailsResponse);
        assertEquals(fileRequest.getFile().getName(), fileDetailsResponse.getFilename());
        assertEquals(fileRequest.getPurpose().getPurpose(), fileDetailsResponse.getPurpose());
        //Provide dispute evidence
        DisputeEvidenceRequest evidenceRequest = DisputeEvidenceRequest.builder()
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
        blocking(getApi().disputesClient().putEvidence(disputeDetails.getId(), evidenceRequest));
        //Retrieve your dispute evidence details
        DisputeEvidenceResponse evidenceResponse = blocking(getApi().disputesClient().getEvidence(disputeDetails.getId()));
        assertNotNull(evidenceResponse);
        assertEquals(evidenceRequest.getProofOfDeliveryOrServiceFile(), evidenceResponse.getProofOfDeliveryOrServiceFile());
        assertEquals(evidenceRequest.getProofOfDeliveryOrServiceText(), evidenceResponse.getProofOfDeliveryOrServiceText());
        assertEquals(evidenceRequest.getProofOfDeliveryOrServiceDateText(), evidenceResponse.getProofOfDeliveryOrServiceDateText());
        //Submit your dispute evidence
        blocking(getApi().disputesClient().submitEvidence(disputeDetails.getId()));
    }

}