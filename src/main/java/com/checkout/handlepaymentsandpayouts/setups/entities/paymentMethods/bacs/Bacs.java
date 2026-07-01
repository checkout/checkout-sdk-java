package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.bacs;

import com.checkout.common.CountryCode;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodInitialization;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Bacs payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Bacs extends PaymentMethodBase {

    /**
     * The initialization state of the payment method. Defaults to disabled.
     * [Optional]
     */
    private PaymentMethodInitialization initialization = PaymentMethodInitialization.DISABLED;

    /**
     * The ID of the Bacs instrument used for the payment.
     * [Optional] readOnly
     */
    private String instrumentId;

    /**
     * The account holder details.
     * [Optional]
     */
    private BacsAccountHolder accountHolder;

    /**
     * The account number of the Bacs Direct Debit account.
     * [Optional] writeOnly
     */
    private String accountNumber;

    /**
     * The sort code of the Bacs Direct Debit account.
     * [Optional] writeOnly
     */
    private String bankCode;

    /**
     * The account's country, as an ISO 3166-1 alpha-2 code.
     * [Optional]
     * min 2 characters, max 2 characters
     */
    private CountryCode country;

    /**
     * The account holder's account currency.
     * [Optional]
     */
    private String currency;

    /**
     * Whether vault may accept a partial match when looking up an existing Bacs instrument for the
     * supplied account details.
     * [Optional]
     * Default: false
     */
    private Boolean allowPartialMatch;
}
