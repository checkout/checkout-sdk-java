package com.checkout.instruments.update;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.BacsPaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The details of the Bacs Direct Debit account being updated.
 *
 * <p>Every property is optional on update, and allowPartialMatch changes meaning: on the store
 * request it instructs the vault, and here it reads back what the vault accepted.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateBacsInstrumentData {

    /**
     * The account number of the Bacs Direct Debit account.
     * [Optional]
     * min 8 characters
     * max 8 characters
     */
    private String accountNumber;

    /**
     * The sort code of the Bacs Direct Debit account.
     * [Optional]
     * min 6 characters
     * max 6 characters
     */
    private String bankCode;

    /**
     * The country of the account, as an ISO 3166-1 alpha-2 code.
     * [Optional]
     * min 2 characters
     * max 2 characters
     */
    private CountryCode country;

    /**
     * The currency of the account.
     * [Optional]
     * min 3 characters
     * max 3 characters
     */
    private Currency currency;

    /**
     * The type of payment. Recurring or Regular.
     * [Optional]
     */
    private BacsPaymentType paymentType;

    /**
     * Whether vault accepted a partial match when looking up the Bacs instrument for the supplied
     * account details.
     * [Optional]
     */
    private Boolean allowPartialMatch;

}
