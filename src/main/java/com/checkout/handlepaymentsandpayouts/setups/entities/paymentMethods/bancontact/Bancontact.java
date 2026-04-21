package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bancontact;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Bancontact payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Bancontact extends PaymentMethodBase {

    /**
     * The account holder's name.
     * [Optional]
     * min 3 characters, max 100 characters
     */
    @SerializedName("account_holder_name")
    private String accountHolderName;
}
