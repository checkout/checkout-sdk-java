package com.checkout;

import com.checkout.apm.bacs.BacsClient;
import com.checkout.apm.ideal.IdealClient;

/**
 * The alternative payment method clients that the current platform exposes.
 */
public interface CheckoutApmApi {

    /**
     * Retrieves iDEAL issuer information.
     *
     * @return the iDEAL client.
     */
    IdealClient idealClient();

    /**
     * Sends Bacs Direct Debit pre-notifications.
     *
     * @return the Bacs Direct Debit client.
     */
    BacsClient bacsClient();

}
