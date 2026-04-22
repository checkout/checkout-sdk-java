package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ach;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.PaymentMethodBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The ACH payment method's details and configuration.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class Ach extends PaymentMethodBase {

    /**
     * The type of Direct Debit account.
     * [Optional]
     */
    private AchAccountType accountType;

    /**
     * The account holder details.
     * [Optional]
     */
    private AchAccountHolder accountHolder;

    /**
     * The account number of the Direct Debit account.
     * [Optional]
     * min 4 characters, max 17 characters
     */
    private String accountNumber;

    /**
     * The bank code of the Direct Debit account.
     * [Optional]
     * min 8 characters, max 9 characters
     */
    private String bankCode;

    /**
     * The two-letter ISO country code of the bank account.
     * [Optional]
     * max 2 characters
     */
    private String country;
}
