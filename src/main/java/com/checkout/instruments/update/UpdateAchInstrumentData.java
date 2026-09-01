package com.checkout.instruments.update;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The details of the ACH account being updated.
 *
 * <p>Every property is optional on update. The shape is identical to the store and retrieve
 * variants, unlike the Bacs Direct Debit instrument data, whose length limits differ per operation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateAchInstrumentData {

    /**
     * The type of Direct Debit account.
     * [Optional]
     */
    private AchInstrumentAccountType accountType;

    /**
     * The account number of the Direct Debit account.
     * [Optional]
     * min 4 characters
     * max 17 characters
     */
    private String accountNumber;

    /**
     * The bank code of the Direct Debit account, also known as the routing number.
     * [Optional]
     * min 8 characters
     * max 9 characters
     */
    private String bankCode;

    /**
     * The currency of the account.
     * [Optional]
     * min 3 characters
     * max 3 characters
     */
    private Currency currency;

    /**
     * The country of the account.
     * [Optional]
     * min 2 characters
     * max 2 characters
     */
    private CountryCode country;

}
