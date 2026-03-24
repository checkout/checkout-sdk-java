package com.checkout.handlepaymentsandpayouts.googlepay.responses;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Response containing the enrollment state of a Google Pay entity.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GooglePayEnrollmentStateResponse extends HttpMetadata {

    /**
     * The current enrollment state of the entity.
     * Possible values: ACTIVE, INACTIVE
     */
    private String state;

}
