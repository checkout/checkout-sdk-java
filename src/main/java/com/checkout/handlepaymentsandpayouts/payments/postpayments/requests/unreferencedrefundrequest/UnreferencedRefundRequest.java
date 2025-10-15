package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest;

import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.source.Source;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.AbstractDestination;
import com.google.gson.annotations.SerializedName;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.instruction.Instruction;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.segment.Segment;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.processing.Processing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Request a payment or payout
 * Send a payment or payout.Note: successful payout requests will always return a 202 response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UnreferencedRefundRequest {

    /**
     * The source of the unreferenced refund.
     * [Required]
     */
    private Source source;

    /**
     * The destination of the unreferenced refund.
     * [Required]
     */
    private AbstractDestination destination;

    /**
     * The amount of the payment
     * [Required]
     * &gt;= 1
     */
    private Integer amount;

    /**
     * The three-letter ISO currency code of the payment.
     * [Required]
     * 3 characters
     */
    private String currency;

    /**
     * The type of the payment
     * [Required]
     */
    @SerializedName("payment_type")
    private String paymentType;

    /**
     * The processing channel identifier
     * [Required]
     * ^(pc)_(\w{26})$
     */
    @SerializedName("processing_channel_id")
    private String processingChannelId;

    /**
     * Additional details about the unreferenced refund instruction.
     * [Optional]
     */
    private Instruction instruction;

    /**
     * An internal reference you can later use to identify this payment
     * [Optional]
     */
    private String reference;

    /**
     * A set of key-value pairs that you can attach to the refund request. It can be useful for storing additional
     * information in a structured format. Note: This object only allows one level of depth, so cannot accept
     * non-primitive data types such as objects or arrays.
     * [Optional]
     */
    private Map<String, Object> metadata = new HashMap<>();

    /**
     * The previous related payment identifier. This could be the ID of the payment that you want to refund.
     * [Optional]
     */
    @SerializedName("previous_payment_id")
    private String previousPaymentId;

    /**
     * The dimension details about business segment for payment request. At least one dimension required.
     * [Optional]
     */
    private Segment segment;

    /**
     * Returns information related to the processing of the payment.
     * [Optional]
     */
    private Processing processing;

}
