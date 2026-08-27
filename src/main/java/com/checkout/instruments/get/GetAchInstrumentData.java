package com.checkout.instruments.get;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.update.AchInstrumentAccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The details of a stored ACH bank account.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class GetAchInstrumentData {

    /**
     * The type of Direct Debit account.
     * [Required]
     */
    private AchInstrumentAccountType accountType;

    /**
     * The account number of the Direct Debit account.
     * [Required]
     * min 4 characters
     * max 17 characters
     */
    private String accountNumber;

    /**
     * The bank code of the Direct Debit account, also known as the routing number.
     * [Required]
     * min 8 characters
     * max 9 characters
     */
    private String bankCode;

    /**
     * The currency of the account.
     * [Required]
     * min 3 characters
     * max 3 characters
     */
    private Currency currency;

    /**
     * The country of the account.
     * [Required]
     * min 2 characters
     * max 2 characters
     */
    private CountryCode country;

}
