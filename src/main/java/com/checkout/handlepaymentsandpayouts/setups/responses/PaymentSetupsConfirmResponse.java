package com.checkout.handlepaymentsandpayouts.setups.responses;

import com.checkout.HttpMetadata;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupSource;
import com.checkout.payments.PaymentStatus;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.Map;

/**
 * Represents a payment setup confirmation response.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSetupsConfirmResponse extends HttpMetadata {

    /**
     * The unique identifier of the payment.
     */
    private String id;

    /**
     * The unique identifier of the action.
     */
    @SerializedName("action_id")
    private String actionId;

    /**
     * The payment amount in the minor currency unit.
     */
    private Long amount;

    /**
     * The currency of the payment.
     */
    private Currency currency;

    /**
     * Whether the payment was approved.
     */
    private Boolean approved;

    /**
     * The current status of the payment.
     */
    private String status;

    /**
     * The authorization code returned by the card issuer.
     */
    @SerializedName("auth_code")
    private String authCode;

    /**
     * The response code.
     */
    @SerializedName("response_code")
    private String responseCode;

    /**
     * The response summary.
     */
    @SerializedName("response_summary")
    private String responseSummary;

    /**
     * 3D Secure information.
     */
    @SerializedName("3ds")
    private Map<String, Object> threeDSecure;

    /**
     * Risk information.
     */
    private Map<String, Object> risk;

    /**
     * The source information for the payment.
     */
    private PaymentSetupSource source;

    /**
     * Customer information.
     */
    private Map<String, Object> customer;

    /**
     * The date and time when the payment was processed.
     */
    @SerializedName("processed_on")
    private Instant processedOn;

    /**
     * A unique identifier that can be used to reference the payment.
     */
    private String reference;

    /**
     * Processing information.
     */
    private Map<String, Object> processing;

    /**
     * The Electronic Commerce Indicator.
     */
    private String eci;

    /**
     * The scheme identifier.
     */
    @SerializedName("scheme_id")
    private String schemeId;

    /**
     * Links to related resources.
     */
    @SerializedName("_links")
    private Map<String, Object> links;
}