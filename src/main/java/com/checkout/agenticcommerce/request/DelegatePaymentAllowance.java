package com.checkout.agenticcommerce.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * The spending constraints that define what the delegated payment token is authorized for.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelegatePaymentAllowance {

    /**
     * Why this allowance is being granted (e.g. one-time use).
     * <p>
     * [Required]
     */
    private AllowanceReason reason;

    /**
     * Maximum chargeable amount in the minor currency unit.
     * <p>
     * [Required]
     */
    @SerializedName("max_amount")
    private Long maxAmount;

    /**
     * ISO 4217 alphabetic currency code.
     * <p>
     * [Required]
     * <p>
     * Length: &gt;= 3 characters
     * <p>
     * Length: &lt;= 3 characters
     */
    private String currency;

    /**
     * Merchant that will process the payment.
     * <p>
     * [Required]
     * <p>
     * Length: &lt;= 256 characters
     */
    @SerializedName("merchant_id")
    private String merchantId;

    /**
     * Checkout session associated with this delegated payment.
     * <p>
     * [Required]
     */
    @SerializedName("checkout_session_id")
    private String checkoutSessionId;

    /**
     * When the delegated payment authorization expires (must be in the future).
     * <p>
     * [Required]
     * <p>
     * Format: date-time (RFC 3339)
     */
    @SerializedName("expires_at")
    private Instant expiresAt;
}
