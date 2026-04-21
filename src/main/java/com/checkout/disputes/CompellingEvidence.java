package com.checkout.disputes;

import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.util.List;

public final class CompellingEvidence {

    /**
     * Whether the evidence concerns merchandise or services.
     * [Required]
     * Enum: "Merchandise" "Services"
     */
    @SerializedName("merchandise_or_service")
    private String merchandiseOrService;

    /**
     * A description of the merchandise or service provided.
     * [Required]
     * min 1 character, max 5000 characters
     */
    @SerializedName("merchandise_or_service_desc")
    private String merchandiseOrServiceDesc;

    /**
     * The date the merchandise or service was provided to the customer.
     * [Required]
     * Format: date-time (RFC 3339)
     */
    @SerializedName("merchandise_or_service_provided_date")
    private Instant merchandiseOrServiceProvidedDate;

    /**
     * The shipping or delivery status of the merchandise.
     * [Optional]
     */
    @SerializedName("shipping_delivery_status")
    private ShippingDeliveryStatusType shippingDeliveryStatus;

    /**
     * The tracking number for the shipment.
     * [Optional]
     * max 50 characters
     */
    @SerializedName("tracking_information")
    private TrackingInformationType trackingInformation;

    /**
     * The customer's account or login identifier.
     * [Optional]
     * max 50 characters
     */
    @SerializedName("user_id")
    private String userId;

    /**
     * The IP address used for the transaction (IPv4 or IPv6).
     * [Optional]
     */
    @SerializedName("ip_address")
    private String ipAddress;

    /**
     * The device identifier used for the transaction.
     * [Optional]
     * min 15 characters, max 64 characters
     */
    @SerializedName("device_id")
    private String deviceId;

    /**
     * The shipping address used for the transaction.
     * [Optional]
     */
    @SerializedName("shipping_address")
    private ShippingAddress shippingAddress;

    /**
     * Prior undisputed transactions between the merchant and the same customer.
     * At least 2 items required.
     * [Required]
     */
    @SerializedName("historical_transactions")
    private List<HistoricalTransactions> historicalTransactions;

}
