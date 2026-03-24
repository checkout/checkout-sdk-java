package com.checkout.compliance;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.compliance.entities.ComplianceRequestRespondedField;
import com.checkout.compliance.entities.ComplianceRequestRespondedFields;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for:
 *   GET  /compliance-requests/{payment_id}
 *   POST /compliance-requests/{payment_id}
 *
 * Compliance requests are triggered by the Checkout.com risk team for specific
 * payments flagged for additional verification. They cannot be created on demand
 * in sandbox. Enable these tests manually with a payment ID that has an open
 * compliance request.
 */
@Disabled("Requires a payment with an open compliance request — enable manually with a valid paymentId")
public class ComplianceTestIT extends SandboxTestFixture {

    private static final String PAYMENT_ID = "pay_replace_with_real_payment_id";

    public ComplianceTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldGetComplianceRequest() {
        validateComplianceDetails(blocking(() ->
                checkoutApi.complianceClient().getComplianceRequest(PAYMENT_ID)));
    }

    @Test
    void shouldRespondToComplianceRequest() {
        final ComplianceRequestDetails response = blocking(() ->
                checkoutApi.complianceClient().respondToComplianceRequest(PAYMENT_ID, createRespondRequest()));

        validateComplianceDetails(response);
    }

    // Sync

    @Test
    void shouldGetComplianceRequestSync() {
        validateComplianceDetails(checkoutApi.complianceClient().getComplianceRequestSync(PAYMENT_ID));
    }

    @Test
    void shouldRespondToComplianceRequestSync() {
        validateComplianceDetails(
                checkoutApi.complianceClient().respondToComplianceRequestSync(PAYMENT_ID, createRespondRequest()));
    }

    // Common methods

    private ComplianceRespondRequest createRespondRequest() {
        return ComplianceRespondRequest.builder()
                .fields(ComplianceRequestRespondedFields.builder()
                        .sender(Collections.singletonList(
                                ComplianceRequestRespondedField.builder()
                                        .name("invoice")
                                        .value("base64_encoded_document")
                                        .build()))
                        .build())
                .build();
    }

    private void validateComplianceDetails(final ComplianceRequestDetails response) {
        assertNotNull(response);
        assertNotNull(response.getPaymentId());
        assertNotNull(response.getStatus());
        assertNotNull(response.getFields());
    }
}
