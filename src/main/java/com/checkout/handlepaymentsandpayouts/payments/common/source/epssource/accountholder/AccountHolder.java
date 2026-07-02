package com.checkout.handlepaymentsandpayouts.payments.common.source.epssource.accountholder;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * account_holder
 * The account holder details
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccountHolder {

    /**
     * The first name of the account holder
     * [Required]
     */
    private String firstName;

    /**
     * The last name of the account holder
     * [Required]
     */
    private String lastName;

}
