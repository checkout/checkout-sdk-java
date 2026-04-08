package com.checkout.agenticcommerce.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * The request body for creating a delegated payment token.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DelegatePaymentRequest {

    /**
     * Card payment method details for the delegated payment.
     * <p>[Required]</p>
     */
    @SerializedName("payment_method")
    private DelegatePaymentMethod paymentMethod;

    /**
     * Spending constraints authorizing use of the delegated payment token.
     * <p>[Required]</p>
     */
    private DelegatePaymentAllowance allowance;

    /**
     * Customer billing address for the delegated payment.
     * <p>[Optional]</p>
     * <p>When provided, {@code name}, {@code line_one}, {@code city}, {@code postal_code}, and {@code country} are required within this object.</p>
     */
    @SerializedName("billing_address")
    private DelegatePaymentBillingAddress billingAddress;

    /**
     * Risk assessment signals from the platform for fraud decisioning.
     * <p>[Required]</p>
     * <p>Type: array of object; each element: {@code type} [Required] (string), {@code score} [Required] (integer), {@code action} [Required] (string).</p>
     */
    @SerializedName("risk_signals")
    private List<RiskSignal> riskSignals;

    /**
     * Key-value metadata attached to the delegated payment request (string values only).
     * <p>[Required]</p>
     * <p>Type: object with string values ({@code additionalProperties}: string).</p>
     */
    private Map<String, Object> metadata;
}
