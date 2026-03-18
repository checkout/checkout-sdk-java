package com.checkout.handlepaymentsandpayouts.flow.responses;

import com.checkout.common.Resource;
import com.checkout.handlepaymentsandpayouts.flow.entities.PaymentMethod;
import com.checkout.handlepaymentsandpayouts.flow.entities.PaymentSessionStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Base class for payment submission responses
 */
@Data
@EqualsAndHashCode(callSuper = true)

public abstract class PaymentSubmissionResponse extends Resource {

    /**
     * The payment identifier.
     */
    private String id;

    /**
     * The payment's status - used for polymorphic deserialization.
     */
    private PaymentSessionStatus status;

    /**
     * The payment method name.
     */
    private PaymentMethod type;
}