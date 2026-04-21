package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.multibanco;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Multibanco payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Multibanco extends PaymentMethodBase {

    /**
     * The account holder's name.
     * [Optional]
     * min 3 characters, max 100 characters
     */
    @SerializedName("account_holder_name")
    private String accountHolderName;
}
