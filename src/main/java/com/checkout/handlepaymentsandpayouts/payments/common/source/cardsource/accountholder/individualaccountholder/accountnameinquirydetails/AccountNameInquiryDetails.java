package com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.accountholder.individualaccountholder.accountnameinquirydetails;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * account_name_inquiry_details
 * Details of the Account Name Inquiry check.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccountNameInquiryDetails {

    /**
     * The result of the first name check.
     * [Optional]
     */
    private FirstNameType firstName;

    /**
     * The result of the middle name check.
     * [Optional]
     */
    private MiddleNameType middleName;

    /**
     * The result of the last name check.
     * [Optional]
     */
    private LastNameType lastName;

}
