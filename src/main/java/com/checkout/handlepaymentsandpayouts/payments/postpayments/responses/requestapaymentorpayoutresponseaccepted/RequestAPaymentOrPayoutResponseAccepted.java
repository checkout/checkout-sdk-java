package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponseaccepted;

import com.checkout.common.Resource;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponseaccepted.destination.Destination;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponseaccepted.destination.instruction.Instruction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Request a payment or payout Response 202
 * Payment asynchronous or further action required
 */
@Getter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public final class RequestAPaymentOrPayoutResponseAccepted extends Resource {

    /**
     * The payment's unique identifier.
     * = 30 characters ^(pay)_(\w{26})$
     * [Required]
     */
    private String id;

    /**
     * The refund status.
     * Enum: "Accepted" "Rejected" "Pending"
     * [Required]
     */
    private StatusType status;

    /**
     * The payment's unique identifier.
     * The reference you provided in the refund request.
     * &lt;= 50 characters
     * [Optional]
     */
    private String reference;

    /**
     * Instruction details for payouts to bank accounts.
     * [Optional]
     */
    private Instruction instruction;

    /**
     * The refund destination.
     * [Optional]
     */
    private Destination destination;

}
