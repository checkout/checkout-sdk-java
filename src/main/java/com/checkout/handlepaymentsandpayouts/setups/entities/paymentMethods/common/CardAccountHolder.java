package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Card account holder information, shared by Card, GooglePay, and ApplePay payment methods.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CardAccountHolder {

    /**
     * The card account holder type.
     * [Optional]
     */
    private CardAccountHolderType type;

    /**
     * The card account holder's first name.
     * [Optional]
     */
    private String firstName;

    /**
     * The card account holder's last name.
     * [Optional]
     */
    private String lastName;

    /**
     * The card account holder's middle name.
     * [Optional]
     */
    private String middleName;

    /**
     * Indicates whether the ANI (account name inquiry) check is performed with the card scheme.
     * [Optional]
     */
    private Boolean accountNameInquiry;
}
