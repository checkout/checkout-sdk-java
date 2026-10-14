package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.applepay;

import com.checkout.common.Phone;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.CardAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentSetupScheme;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.WalletToken;
import com.checkout.payments.PreferredSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;

/**
 * The Apple Pay payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class ApplePay extends PaymentMethodBase {

    /**
     * The Apple Pay encrypted payment token data.
     * [Optional] writeOnly
     */
    private ApplePayTokenData tokenData;

    /**
     * The Checkout.com Apple Pay token.
     * [Optional]
     */
    private WalletToken token;

    /**
     * Set to true if you intend to reuse the payment credentials in subsequent payments. If set to
     * false, a payment instrument will not be included in the payment response.
     * [Optional] writeOnly
     */
    private Boolean storeForFutureUse;

    /**
     * The customer's phone number.
     * [Optional]
     */
    private Phone phone;

    /**
     * The card account holder details.
     * [Optional]
     */
    private CardAccountHolder accountHolder;

    /**
     * The list of supported card scheme names.
     * [Optional] readOnly
     */
    private List<PaymentSetupScheme> schemes;

    /**
     * The preferred card scheme to process the payment.
     * [Optional]
     */
    private PreferredSchema preferredScheme;

    /**
     * @deprecated Use {@link WalletToken#getExpiresOn()} through {@link #getToken()}. The API
     * expects this value under {@code token} and drops it at the Apple Pay level.
     */
    @Deprecated
    public Instant getExpiresOn() {
        return token == null ? null : token.getExpiresOn();
    }

    /**
     * @deprecated Use {@link WalletToken#setExpiresOn(Instant)} through {@link #setToken(WalletToken)}.
     * The API expects this value under {@code token}; this setter now writes it there.
     */
    @Deprecated
    public void setExpiresOn(final Instant expiresOn) {
        if (token == null) {
            token = new WalletToken();
        }
        token.setExpiresOn(expiresOn);
    }
}
