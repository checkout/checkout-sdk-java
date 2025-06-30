package com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SubmitPaymentSessionRequest {

    /**
     * A unique token representing the additional customer data captured by Flow, as received from the handleSubmit
     * callback. Do not log or store this value.
     */
    @SerializedName("session_data")
    private String sessionData;

    /**
     * The payment amount. Provide a value of 0 to perform a card verification. The amount must be provided in the minor
     * currency unit.
     */
    private Long amount;

    /**
     * A reference you can use to identify the payment. For example, an order number. For Amex payments, this must be at
     * most 30 characters. For Benefit payments, the reference must be a unique alphanumeric value. For iDEAL payments,
     * the reference is required and must be an alphanumeric value with a 35-character limit.
     */
    private String reference;

    /**
     * The line items in the order.
     */
    private List<Item> items;

    /**
     * Information required for 3D Secure authentication payments.
     */
    @SerializedName("3ds")
    private Threeds threeds;

    /**
     * The Customer's IP address. Only IPv4 and IPv6 addresses are accepted.
     */
    @SerializedName("ip_address")
    private String ipAddress;

}
