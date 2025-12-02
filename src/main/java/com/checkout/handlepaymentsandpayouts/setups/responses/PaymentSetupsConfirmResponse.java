package com.checkout.handlepaymentsandpayouts.setups.responses;

import com.checkout.HttpMetadata;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentStatus;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.Map;

/**
 * Payment setup confirmation response
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSetupsConfirmResponse extends HttpMetadata {

    /**
     * The payment's unique identifier
     */
    private String id;

    /**
     * The unique identifier for the action performed against this payment
     */
    @SerializedName("action_id")
    private String actionId;

    /**
     * The payment amount
     */
    private Long amount;

    /**
     * The three-letter ISO currency code of the payment
     */
    private Currency currency;

    /**
     * Whether or not the authorization or capture was successful
     */
    private Boolean approved;

    /**
     * The status of the payment
     */
    private PaymentStatus status;

    /**
     * The acquirer authorization code if the payment was authorized
     */
    @SerializedName("auth_code")
    private String authCode;

    /**
     * The Gateway response code
     */
    @SerializedName("response_code")
    private String responseCode;

    /**
     * The Gateway response summary
     */
    @SerializedName("response_summary")
    private String responseSummary;

    /**
     * Provides 3D Secure enrollment status if the payment was downgraded to non-3D Secure
     */
    @SerializedName("3ds")
    private Map<String, Object> threeDSecure;

    /**
     * Information relating to the risk assessment of the payment
     */
    private Map<String, Object> risk;

    /**
     * The source of the payment
     */
    private PaymentSetupSource source;

    /**
     * The customer's details
     */
    private Map<String, Object> customer;

    /**
     * The date and time at which the payment was processed
     */
    @SerializedName("processed_on")
    private Instant processedOn;

    /**
     * Your reference for the payment
     */
    private String reference;

    /**
     * The processing information
     */
    private Map<String, Object> processing;

    /**
     * Electronic Commerce Indicator
     */
    private String eci;

    /**
     * The scheme transaction identifier
     */
    @SerializedName("scheme_id")
    private String schemeId;
}