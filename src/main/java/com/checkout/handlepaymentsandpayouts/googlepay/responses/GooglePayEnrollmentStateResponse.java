package com.checkout.handlepaymentsandpayouts.googlepay.responses;

import lombok.Data;

/**
 * Response containing the enrollment state of a Google Pay entity.
 */
@Data
public final class GooglePayEnrollmentStateResponse {

    /**
     * The current enrollment state of the entity.
     * Possible values: ACTIVE, INACTIVE
     */
    private String state;

}
