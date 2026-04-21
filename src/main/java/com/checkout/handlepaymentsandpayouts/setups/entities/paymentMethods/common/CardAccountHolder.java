package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("first_name")
    private String firstName;

    /**
     * The card account holder's last name.
     * [Optional]
     */
    @SerializedName("last_name")
    private String lastName;

    /**
     * The card account holder's middle name.
     * [Optional]
     */
    @SerializedName("middle_name")
    private String middleName;

    /**
     * Indicates whether the ANI (account name inquiry) check is performed with the card scheme.
     * [Optional]
     */
    @SerializedName("account_name_inquiry")
    private Boolean accountNameInquiry;
}
