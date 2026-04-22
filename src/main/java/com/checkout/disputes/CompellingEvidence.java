package com.checkout.disputes;

import java.time.Instant;
import java.util.List;

public final class CompellingEvidence {

    /**
     * Whether the evidence concerns merchandise or services.
     * [Required]
     * Enum: "Merchandise" "Services"
     */
    private String merchandiseOrService;

    /**
     * A description of the merchandise or service provided.
     * [Required]
     * min 1 character, max 5000 characters
     */
    private String merchandiseOrServiceDesc;

    /**
     * The date the merchandise or service was provided to the customer.
     * [Required]
     * Format: date-time (RFC 3339)
     */
    private Instant merchandiseOrServiceProvidedDate;

    /**
     * The shipping or delivery status of the merchandise.
     * [Optional]
     */
    private ShippingDeliveryStatusType shippingDeliveryStatus;

    /**
     * The tracking number for the shipment.
     * [Optional]
     * max 50 characters
     */
    private TrackingInformationType trackingInformation;

    /**
     * The customer's account or login identifier.
     * [Optional]
     * max 50 characters
     */
    private String userId;

    /**
     * The IP address used for the transaction (IPv4 or IPv6).
     * [Optional]
     */
    private String ipAddress;

    /**
     * The device identifier used for the transaction.
     * [Optional]
     * min 15 characters, max 64 characters
     */
    private String deviceId;

    /**
     * The shipping address used for the transaction.
     * [Optional]
     */
    private ShippingAddress shippingAddress;

    /**
     * Prior undisputed transactions between the merchant and the same customer.
     * At least 2 items required.
     * [Required]
     */
    private List<HistoricalTransactions> historicalTransactions;

}
