package com.checkout.handlepaymentsandpayouts.flow.entities;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Configuration options for payment methods
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentMethodConfiguration {

    /**
     * Configuration options specific to Apple Pay payments.
     * [Optional]
     */
    @SerializedName("applepay")
    private ApplePayConfiguration applePay;

    /**
     * Configuration options specific to card payments.
     * [Optional]
     */
    private CardConfiguration card;

    /**
     * Configuration options specific to Google Pay payments.
     * [Optional]
     */
    @SerializedName("googlepay")
    private GooglePayConfiguration googlePay;

    /**
     * Configuration options specific to stored card payments.
     * [Optional]
     */
    private StoredCardConfiguration storedCard;
}
