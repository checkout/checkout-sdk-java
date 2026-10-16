package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.googlepay;

import com.checkout.common.Phone;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.CardAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentSetupScheme;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.WalletToken;
import com.checkout.payments.PreferredSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * The Google Pay payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class GooglePay extends PaymentMethodBase {

    /**
     * The Google Pay encrypted payment token data.
     * [Optional] writeOnly
     */
    private GooglePayTokenData tokenData;

    /**
     * The Checkout.com Google Pay token.
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
     * Google Pay accepts only VISA and MASTERCARD.
     * [Optional]
     */
    private PreferredSchema preferredScheme;
}
