package com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.responses;

import com.checkout.common.Resource;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.enums.PaymentMethodType;
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
public final class SubmitPaymentSessionResponse extends Resource {

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
     * The reason for the payment decline.
     */
    @SerializedName("decline_reason")
    private String declineReason;

    /**
     * Instruction for further payment action.
     */
    private Object action;

}
