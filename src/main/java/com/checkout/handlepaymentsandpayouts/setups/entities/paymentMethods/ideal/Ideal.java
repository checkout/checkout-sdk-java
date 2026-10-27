package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ideal;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The iDEAL payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Ideal extends PaymentMethodBase {

    /**
     * A description of the products or services being paid for.
     * Do not include HTML tags or special characters.
     * The specification marks this optional, but the API keeps the method invalid until it is sent.
     * [Optional]
     * max 35 characters
     */
    private String description;
}
