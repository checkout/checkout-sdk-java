package com.checkout.handlepaymentsandpayouts.setups.entities.accountFundingTransaction;

import com.checkout.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Account funding transaction recipient details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccountFundingTransactionRecipient {

    /**
     * Date of birth of the recipient.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate dateOfBirth;

    /**
     * Any identifier like part of the PAN (first six digits and last four digits), an IBAN,
     * an internal account number, or a phone number related to the primary recipient's account.
     * [Optional]
     * max 34 characters
     */
    private String accountNumber;

    /**
     * The recipient's first name.
     * [Optional]
     * max 50 characters
     */
    private String firstName;

    /**
     * The recipient's last name.
     * [Optional]
     * max 50 characters
     */
    private String lastName;

    /**
     * The recipient's address.
     * [Optional]
     */
    private Address address;
}
