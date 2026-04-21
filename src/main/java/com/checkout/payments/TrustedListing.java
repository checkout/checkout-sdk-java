package com.checkout.payments;

import lombok.Data;

@Data
public final class TrustedListing {

    /**
     * The trusted listing status for the cardholder.
     * [Optional]
     */
    private String status;

    /**
     * The source of the trusted listing entry.
     * [Optional]
     */
    private String source;

}
