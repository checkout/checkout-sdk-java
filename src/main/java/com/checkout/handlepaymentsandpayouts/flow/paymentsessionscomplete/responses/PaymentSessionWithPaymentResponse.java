package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.responses;

import com.checkout.common.Resource;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.enums.PaymentMethodType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSessionWithPaymentResponse extends Resource {

    /**
     * The payment identifier.
     */
    private String id;

    /**
     * The payment's status. ApprovedDeclinedApproved
     */
    private String status;

    /**
     * The payment method name.
     */
    private PaymentMethodType type;

    /**
     * Instruction for further payment action.
     */
    private Object action;

    /**
     * The Payment Sessions unique identifier
     */
    @SerializedName("payment_session_id")
    private String paymentSessionId;

    /**
     * The secret used by Flow to authenticate payment session requests. Do not log or store this value.
     */
    @SerializedName("payment_session_secret")
    private String paymentSessionSecret;

    /**
     * The reason for the payment decline.
     */
    @SerializedName("decline_reason")
    private String declineReason;

}
