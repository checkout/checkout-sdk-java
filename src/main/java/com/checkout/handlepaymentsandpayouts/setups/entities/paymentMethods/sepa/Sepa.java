package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.sepa;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import com.checkout.common.Currency;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The SEPA payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Sepa extends PaymentMethodBase {

    /**
     * The account holder details.
     * [Optional]
     */
    @SerializedName("account_holder")
    private SepaAccountHolder accountHolder;

    /**
     * The account holder's IBAN.
     * [Optional]
     */
    @SerializedName("account_number")
    private String accountNumber;

    /**
     * The account's country as an ISO 3166-1 alpha-2 code.
     * [Optional]
     * max 2 characters
     */
    private String country;

    /**
     * The account holder's account currency.
     * [Optional]
     */
    private Currency currency;

    /**
     * The SEPA mandate details.
     * [Optional]
     */
    private SepaMandate mandate;
}
