package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.card;

import com.checkout.common.Phone;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.CardAccountHolder;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentSetupScheme;
import com.checkout.payments.PreferredSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * The Card payment method's details and configuration. The card data lives under
 * {@link #getDetails()}, which maps {@code card.details}.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Card extends PaymentMethodBase {

    /**
     * The Checkout.com card token to use instead of the raw card details.
     * [Optional]
     */
    private CardToken token;

    /**
     * The card details.
     * [Optional]
     */
    private CardDetails details;

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
}
