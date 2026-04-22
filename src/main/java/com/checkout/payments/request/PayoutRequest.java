package com.checkout.payments.request;

import com.checkout.common.Currency;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.request.destination.PaymentRequestDestination;
import com.checkout.payments.request.source.PayoutRequestSource;
import com.checkout.payments.sender.PaymentSender;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public final class PayoutRequest {

    /**
     * The source of the payout. Discriminated by type.
     * [Required]
     */
    private PayoutRequestSource source;

    /**
     * The destination of the payout. Discriminated by type.
     * [Required]
     */
    private PaymentRequestDestination destination;

    /**
     * The amount to pay out in minor currency units.
     * [Optional]
     * min 0
     */
    private Long amount;

    /**
     * The three-letter ISO 4217 currency code for the payout currency.
     * [Required]
     */
    private Currency currency;

    /**
     * A custom reference to identify the payout, such as an order number.
     * [Optional]
     * max 50 characters
     */
    private String reference;

    /**
     * An optional description displayed on the recipient's statement identifying the payout.
     * [Optional]
     */
    private PayoutBillingDescriptor billingDescriptor;

    /**
     * The sender of the payout. Required for financial regulations in some jurisdictions.
     * [Optional]
     */
    private PaymentSender sender;

    /**
     * Additional details about the payout instruction, such as purpose of payment.
     * [Required]
     */
    private PaymentInstruction instruction;

    /**
     * The processing channel to be used for the payout.
     * [Required]
     * Pattern: ^(pc)_(\w{26})$
     * max 29 characters
     */
    private String processingChannelId;

    /**
     * Use the processing object to influence or override data during payout processing.
     * [Optional]
     */
    private ProcessingSettings processing;

    /**
     * The business segment dimension details for this payout.
     * At least one dimension (brand, business_category, or market) required.
     * [Optional]
     */
    private PaymentSegment segment;

    /**
     * Key-value pairs to store additional information about the payout.
     * Supports primitive data types only; objects and arrays are not supported.
     * [Optional]
     */
    private Map<String, Object> metadata;

}
