package com.checkout.compliance;

import com.checkout.HttpMetadata;
import com.checkout.compliance.entities.RequestedFields;
import com.google.gson.annotations.SerializedName;
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

    @SerializedName("payment_id")
    private String paymentId;

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("entity_id")
    private String entityId;

    @SerializedName("segment_id")
    private String segmentId;

    private String amount;

    private String currency;

    @SerializedName("recipient_name")
    private String recipientName;

    @SerializedName("requested_on")
    private String requestedOn;

    private String status;

    private RequestedFields fields;

    @SerializedName("transaction_reference")
    private String transactionReference;

    @SerializedName("sender_reference")
    private String senderReference;

    @SerializedName("last_updated")
    private String lastUpdated;

    @SerializedName("sender_name")
    private String senderName;

    @SerializedName("payment_type")
    private String paymentType;

}
