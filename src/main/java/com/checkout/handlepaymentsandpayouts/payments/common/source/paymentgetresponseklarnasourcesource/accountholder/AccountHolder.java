package com.checkout.handlepaymentsandpayouts.payments.common.source.paymentgetresponseklarnasourcesource.accountholder;

import com.checkout.handlepaymentsandpayouts.payments.common.source.paymentgetresponseklarnasourcesource.accountholder.billingaddress.BillingAddress;
import com.checkout.handlepaymentsandpayouts.payments.common.source.paymentgetresponseklarnasourcesource.accountholder.phone.Phone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * account_holder
 * object describes payee details
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccountHolder {

    /**
     * First name of the account holder.
     * [Optional]
     */
    private String firstName;

    /**
     * Last name of the account holder.
     * [Optional]
     */
    private String lastName;

    /**
     * Address of the account holder.
     * [Optional]
     */
    private BillingAddress billingAddress;

    /**
     * Email address of the account holder.
     * [Optional]
     */
    private String email;

    /**
     * Date of birth of the account holder.
     * [Optional]
     */
    private String dateOfBirth;

    /**
     * Gender of the account holder.
     * [Optional]
     */
    private String gender;

    /**
     * Phone number of the account holder.
     * [Optional]
     */
    private Phone phone;

}
