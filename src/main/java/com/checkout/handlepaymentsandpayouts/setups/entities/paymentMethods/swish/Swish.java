package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.swish;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.google.gson.annotations.SerializedName;
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
    @SerializedName("billing_descriptor")
    private String billingDescriptor;

    /**
     * The account holder's details.
     * [Optional]
     */
    @SerializedName("account_holder")
    private SwishAccountHolder accountHolder;
}
