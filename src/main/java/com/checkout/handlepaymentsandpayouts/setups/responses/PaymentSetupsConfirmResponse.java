package com.checkout.handlepaymentsandpayouts.setups.responses;

import com.checkout.HttpMetadata;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupSource;
import com.checkout.payments.PaymentStatus;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
     * The unique identifier of the payment setup.
     */
    private String id;

    /**
     * A unique identifier that can be used to reference the payment setup.
     */
    private String reference;

    /**
     * The current status of the payment setup.
     */
    private PaymentStatus status;

    /**
     * The source information for the payment setup.
     */
    private PaymentSetupSource source;

    /**
     * The session identifier.
     */
    @SerializedName("session_id")
    private String sessionId;
}