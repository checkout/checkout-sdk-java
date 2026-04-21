package com.checkout.payments.response.source;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.checkout.common.PaymentSourceType.PAYPAL;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PayPalResponseSource extends AbstractResponseSource implements ResponseSource {

    /**
     * The PayPal account holder details.
     * [Optional]
     */
    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    public PayPalResponseSource() {
        this.type = PAYPAL;
    }

    @Data
    public static final class AccountHolder {

        /**
         * The PayPal account holder's email address.
         * [Optional]
         */
        private String email;

        /**
         * The PayPal account holder's full name.
         * [Optional]
         */
        @SerializedName("full_name")
        private String fullName;

    }

}
