package com.checkout.handlepaymentsandpayouts.googlepay.responses;

import com.checkout.HttpMetadata;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

/**
 * Response returned when enrolling an entity in Google Pay.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GooglePayEnrollmentResponse extends HttpMetadata {

    /**
     * When the Google terms of service were accepted.
     * <p>
     * [Required]
     * </p>
     * Format: date-time (RFC 3339)
     */
    @SerializedName("tos_accepted_time")
    private Instant tosAcceptedTime;

    /**
     * The current enrollment state of the entity.
     * <p>
     * [Required]
     * </p>
     */
    private GooglePayEnrollmentState state;

}
