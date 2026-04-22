package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.swish;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Swish payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Swish extends PaymentMethodBase {

    /**
     * A description that appears on the customer's billing statement.
     * [Optional]
     */
    private String billingDescriptor;

    /**
     * The account holder's details.
     * [Optional]
     */
    private SwishAccountHolder accountHolder;
}
