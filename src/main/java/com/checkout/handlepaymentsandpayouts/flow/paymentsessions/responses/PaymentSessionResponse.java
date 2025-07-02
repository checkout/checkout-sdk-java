package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.responses;

import com.checkout.common.Resource;
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
public final class PaymentSessionResponse extends Resource {

    /**
     * The Payment Sessions unique identifier
     */
    private String id;

    /**
     * A unique token representing the payment session, which you must provide when you initialize Flow. Do not log or
     * store this value.
     */
    @SerializedName("payment_session_token")
    private String paymentSessionToken;

    /**
     * The secret used by Flow to authenticate payment session requests. Do not log or store this value.
     */
    @SerializedName("payment_session_secret")
    private String paymentSessionSecret;

}
