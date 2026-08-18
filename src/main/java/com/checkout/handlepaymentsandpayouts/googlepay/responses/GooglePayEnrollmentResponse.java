package com.checkout.handlepaymentsandpayouts.googlepay.responses;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

/**
 * Response returned when enrolling an entity in Google Pay.
 *
 * <p>The real 201 body carries merchant_id, tos_accepted_time and state. The spec declares only
 * {@code tosAcceptedTime} and {@code state}, with {@code additionalProperties false}, so
 * {@code merchant_id} was missing here: this class was generated faithfully from a wrong schema.
 * Reported by a merchant. The spec is being fixed separately; until then this class follows the
 * live API.</p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GooglePayEnrollmentResponse extends HttpMetadata {

    /**
     * The Google Pay merchant identifier assigned to the entity, needed to initialise Google Pay
     * on the client. Returned by the API but absent from the spec.
     */
    private String merchantId;

    /**
     * When the Google terms of service were accepted.
     * <p>
     * [Required]
     * </p>
     * Format: date-time (RFC 3339)
     */
    private Instant tosAcceptedTime;

    /**
     * The current enrollment state of the entity.
     * <p>
     * [Required]
     * </p>
     */
    private GooglePayEnrollmentState state;

}
