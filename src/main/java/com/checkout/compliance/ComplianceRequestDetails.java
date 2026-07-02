package com.checkout.compliance;

import com.checkout.HttpMetadata;
import com.checkout.compliance.entities.RequestedFields;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represents a compliance request with all its details.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ComplianceRequestDetails extends HttpMetadata {

    private String paymentId;

    private String clientId;

    private String entityId;

    private String segmentId;

    private String amount;

    private String currency;

    private String recipientName;

    private String requestedOn;

    private String status;

    private RequestedFields fields;

    private String transactionReference;

    private String senderReference;

    private String lastUpdated;

    private String senderName;

    private String paymentType;

}
