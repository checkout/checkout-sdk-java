package com.checkout.payments.response;

import lombok.Data;

/**
 * Provides information relating to the authentication of the payment.
 * <p>
 * [Optional]
 * </p>
 */
@Data
public class PaymentResponseAuthentication {

    /**
     * The authentication experience that was used to authenticate the payment.
     * <p>
     * [Optional]
     * </p>
     */
    private AuthenticationExperience experience;

}
