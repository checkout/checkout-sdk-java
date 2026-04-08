package com.checkout.agenticcommerce.response;

import com.checkout.HttpMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.util.Map;

/**
 * The response returned when a delegated payment token is successfully created.
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DelegatePaymentResponse extends HttpMetadata {

    /**
     * Unique identifier of the provisioned delegated payment token.
     * <p>
     * [Required]
     */
    private String id;

    /**
     * Timestamp when the token was created.
     * <p>
     * [Required]
     * <p>
     * Format: date-time (RFC 3339)
     */
    private Instant created;

    /**
     * Response metadata (string values only).
     * <p>
     * [Required]
     */
    private Map<String, Object> metadata;
}
