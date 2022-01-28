package com.checkout.disputes;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.FileDetailsResponse;
import com.checkout.common.FilePurpose;
import com.checkout.common.FileRequest;
import com.checkout.common.IdResponse;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DisputesClientImplTest {

    private static final String DISPUTES = "/disputes";
    private static final String FILES = "/files";
    private static final String EVIDENCE = "evidence";
    private static final String REASON_CODE = "10.4";
    private static final long PAYMENT_AMOUNT = 1040L;
    private static final String PAYMENT_ARN = "802355416667";
    private static final String FILE_ID = "file_7kxknf5ftrgurfouydbbs74gvq";
    private static final String FILE_NAME = "my_evidence.jpg";
    private static final String PAYMENT_ID = "pay_3216549s87jhhjhguyt";
    private static final String DISPUTE_ID = "dsp_9ec11deafd679h77356a";

    private DisputesClient client;

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @Mock
    private Dispute dispute;

    @Mock
    private DisputesQueryResponse disputesQueryResponse;

    @Mock
    private DisputeDetailsResponse disputeDetailsResponse;

    @Mock
    private DisputeEvidenceResponse evidenceResponse;

    @Mock
    private IdResponse idResponse;

    @Mock
    private FileDetailsResponse fileDetailsResponse;

    @Mock
    private CompletableFuture<DisputesQueryResponse> disputesQueryResponseFuture;

    @Mock
    private CompletableFuture<DisputeDetailsResponse> disputeDetailsFuture;

    @Mock
    private CompletableFuture<DisputeEvidenceResponse> evidenceResponseFuture;

    @Mock
    private CompletableFuture<IdResponse> idResponseFuture;

    @Mock
    private CompletableFuture<FileDetailsResponse> fileDetailsResponseFuture;

    private PaymentDispute paymentDispute;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        disputesQueryResponseFuture = CompletableFuture.completedFuture(disputesQueryResponse);
        disputeDetailsFuture = CompletableFuture.completedFuture(disputeDetailsResponse);
        evidenceResponseFuture = CompletableFuture.completedFuture(evidenceResponse);
        idResponseFuture = CompletableFuture.completedFuture(idResponse);
        fileDetailsResponseFuture = CompletableFuture.completedFuture(fileDetailsResponse);
        client = new DisputesClientImpl(apiClient, configuration);
        paymentDispute = PaymentDispute.builder().id(PAYMENT_ID).amount(PAYMENT_AMOUNT).arn(PAYMENT_ARN).build();
    }

    @Test
    void shouldPerformQuery() throws ExecutionException, InterruptedException {
        final DisputesQueryFilter request = DisputesQueryFilter.builder().paymentId(PAYMENT_ID).limit(250).build();
        doReturn(disputesQueryResponseFuture)
                .when(apiClient)
                .queryAsync(eq(DISPUTES), eq(authorization),
                        any(DisputesQueryFilter.class), eq(DisputesQueryResponse.class));
        when(disputesQueryResponse.getLimit()).thenReturn(request.getLimit());
        when(disputesQueryResponse.getTotalCount()).thenReturn(1);
        when(disputesQueryResponse.getData()).thenReturn(Collections.singletonList(dispute));
        when(dispute.getId()).thenReturn(DISPUTE_ID);
        when(dispute.getCategory()).thenReturn(DisputeCategory.FRAUDULENT);
        when(dispute.getPaymentId()).thenReturn(PAYMENT_ID);

        final DisputesQueryResponse response = client.query(request).get();
        assertNotNull(response);
        assertEquals(request.getLimit(), response.getLimit());
        assertEquals(1, response.getTotalCount().intValue());
        assertNotNull(response.getData());
        assertEquals(DISPUTE_ID, response.getData().get(0).getId());
        assertEquals(DisputeCategory.FRAUDULENT, response.getData().get(0).getCategory());
        assertEquals(PAYMENT_ID, response.getData().get(0).getPaymentId());
    }

    @Test
    void shouldGetDisputeDetails() throws ExecutionException, InterruptedException {
        doReturn(disputeDetailsFuture)
                .when(apiClient)
                .getAsync(eq(getPath(DISPUTES, DISPUTE_ID)), eq(authorization), eq(DisputeDetailsResponse.class));
        when(disputeDetailsResponse.getId()).thenReturn(DISPUTE_ID);
        when(disputeDetailsResponse.getCategory()).thenReturn(DisputeCategory.FRAUDULENT);
        when(disputeDetailsResponse.getReasonCode()).thenReturn(REASON_CODE);
        when(disputeDetailsResponse.getStatus()).thenReturn(DisputeStatus.EVIDENCE_REQUIRED);
        when(disputeDetailsResponse.getPayment()).thenReturn(paymentDispute);
        final DisputeDetailsResponse response = client.getDisputeDetails(DISPUTE_ID).get();
        assertNotNull(response);
        assertEquals(DISPUTE_ID, response.getId());
        assertEquals(DisputeCategory.FRAUDULENT, response.getCategory());
        assertEquals(REASON_CODE, response.getReasonCode());
        assertEquals(DisputeStatus.EVIDENCE_REQUIRED, response.getStatus());
        assertNotNull(response.getPayment());
        assertEquals(PAYMENT_ID, response.getPayment().getId());
        assertEquals(PAYMENT_AMOUNT, response.getPayment().getAmount().longValue());
        assertEquals(PAYMENT_ARN, response.getPayment().getArn());
    }

    @Test
    void shouldUploadFile() throws ExecutionException, InterruptedException {
        final FileRequest request = FileRequest.builder()
                .file(new File(FILE_NAME))
                .contentType(ContentType.IMAGE_JPEG)
                .purpose(FilePurpose.DISPUTE_EVIDENCE).build();
        doReturn(idResponseFuture)
                .when(apiClient)
                .submitFileAsync(eq(FILES), eq(authorization), any(FileRequest.class),
                        eq(IdResponse.class));
        when(idResponse.getId()).thenReturn(FILE_ID);
        final IdResponse response = client.uploadFile(request).get();
        assertNotNull(response);
        assertEquals(FILE_ID, response.getId());
    }

    @Test
    void shouldGetEvidence() throws ExecutionException, InterruptedException {
        doReturn(evidenceResponseFuture)
                .when(apiClient)
                .getAsync(eq(getPath(getPath(DISPUTES, DISPUTE_ID), EVIDENCE)),
                        eq(authorization), eq(DisputeEvidenceResponse.class));
        when(evidenceResponse.getProofOfDeliveryOrServiceFile()).thenReturn(FILE_NAME);
        final String TEXT_MESSAGE = "text_message";
        when(evidenceResponse.getProofOfDeliveryOrServiceText()).thenReturn(TEXT_MESSAGE);
        when(evidenceResponse.getInvoiceOrReceiptFile()).thenReturn(FILE_NAME);
        when(evidenceResponse.getInvoiceOrReceiptText()).thenReturn(TEXT_MESSAGE);
        when(evidenceResponse.getInvoiceShowingDistinctTransactionsFile()).thenReturn(FILE_NAME);
        when(evidenceResponse.getInvoiceShowingDistinctTransactionsText()).thenReturn(TEXT_MESSAGE);
        when(evidenceResponse.getCustomerCommunicationFile()).thenReturn(FILE_NAME);
        when(evidenceResponse.getCustomerCommunicationText()).thenReturn(TEXT_MESSAGE);
        when(evidenceResponse.getRefundOrCancellationPolicyFile()).thenReturn(FILE_NAME);
        when(evidenceResponse.getRefundOrCancellationPolicyText()).thenReturn(TEXT_MESSAGE);
        when(evidenceResponse.getRecurringTransactionAgreementFile()).thenReturn(FILE_NAME);
        when(evidenceResponse.getRecurringTransactionAgreementText()).thenReturn(TEXT_MESSAGE);
        when(evidenceResponse.getAdditionalEvidenceFile()).thenReturn(FILE_NAME);
        when(evidenceResponse.getAdditionalEvidenceText()).thenReturn(TEXT_MESSAGE);
        when(evidenceResponse.getProofOfDeliveryOrServiceDateFile()).thenReturn(FILE_NAME);
        when(evidenceResponse.getProofOfDeliveryOrServiceDateText()).thenReturn(TEXT_MESSAGE);
        final DisputeEvidenceResponse response = client.getEvidence(DISPUTE_ID).get();
        assertNotNull(response);
        assertEquals(FILE_NAME, response.getProofOfDeliveryOrServiceFile());
        assertEquals(TEXT_MESSAGE, response.getProofOfDeliveryOrServiceText());
        assertEquals(FILE_NAME, response.getInvoiceOrReceiptFile());
        assertEquals(TEXT_MESSAGE, response.getInvoiceOrReceiptText());
        assertEquals(FILE_NAME, response.getInvoiceShowingDistinctTransactionsFile());
        assertEquals(TEXT_MESSAGE, response.getInvoiceShowingDistinctTransactionsText());
        assertEquals(FILE_NAME, response.getCustomerCommunicationFile());
        assertEquals(TEXT_MESSAGE, response.getCustomerCommunicationText());
        assertEquals(FILE_NAME, response.getRefundOrCancellationPolicyFile());
        assertEquals(TEXT_MESSAGE, response.getProofOfDeliveryOrServiceText());
        assertEquals(FILE_NAME, response.getProofOfDeliveryOrServiceFile());
        assertEquals(TEXT_MESSAGE, response.getRefundOrCancellationPolicyText());
        assertEquals(FILE_NAME, response.getRecurringTransactionAgreementFile());
        assertEquals(TEXT_MESSAGE, response.getRecurringTransactionAgreementText());
        assertEquals(FILE_NAME, response.getAdditionalEvidenceFile());
        assertEquals(TEXT_MESSAGE, response.getAdditionalEvidenceText());
        assertEquals(FILE_NAME, response.getProofOfDeliveryOrServiceDateFile());
        assertEquals(TEXT_MESSAGE, response.getProofOfDeliveryOrServiceDateText());
    }

    @Test
    void shouldGetFileDetails() throws ExecutionException, InterruptedException {
        doReturn(fileDetailsResponseFuture)
                .when(apiClient)
                .getAsync(eq(getPath(FILES, FILE_ID)), eq(authorization), eq(FileDetailsResponse.class));
        when(fileDetailsResponse.getFilename()).thenReturn(FILE_NAME);
        when(fileDetailsResponse.getId()).thenReturn(FILE_ID);
        when(fileDetailsResponse.getPurpose()).thenReturn(FilePurpose.DISPUTE_EVIDENCE.getPurpose());
        final FileDetailsResponse response = client.getFileDetails(FILE_ID).get();
        assertNotNull(response);
        assertEquals(FILE_ID, response.getId());
        assertEquals(FILE_NAME, response.getFilename());
        assertEquals(FilePurpose.DISPUTE_EVIDENCE.getPurpose(), response.getPurpose());
    }

    private String getPath(final String source, final String param) {
        return source + "/" + param;
    }

}