package com.checkout.payments.response.source;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import static com.checkout.common.PaymentSourceType.PAYPAL;

/**
 * PayPal source.
 *
 * <p>The type and the id are inherited from {@link AbstractResponseSource}.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PayPalResponseSource extends AbstractResponseSource implements ResponseSource {

    /**
     * The PayPal account holder details.
     * [Optional]
     */
    private AccountHolder accountHolder;

    public PayPalResponseSource() {
        this.type = PAYPAL;
    }

    /**
     * The PayPal account holder details.
     */
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
        private String fullName;

    }

}
