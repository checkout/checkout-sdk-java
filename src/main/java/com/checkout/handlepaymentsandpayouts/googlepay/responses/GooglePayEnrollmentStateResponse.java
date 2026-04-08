package com.checkout.handlepaymentsandpayouts.googlepay.responses;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GooglePayEnrollmentStateResponse extends HttpMetadata {

    /**
     * The current enrollment state of the entity.
     * <p>
     * [Required]
     * </p>
     */
    private GooglePayEnrollmentState state;

}
