package com.checkout.instruments.create;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.BacsPaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The details of the Bacs Direct Debit account being stored.
 *
 * <p>This is not the SEPA instrument data shape: the account number is a fixed-length UK account
 * number rather than an IBAN, the sort code arrives in {@code bank_code}, and there is no mandate
 * type, mandate ID or date of signature.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CreateBacsInstrumentData {

    /**
     * The account number of the Bacs Direct Debit account.
     * [Required]
     * min 8 characters
     * max 8 characters
     */
    private String accountNumber;

    /**
     * The sort code of the Bacs Direct Debit account.
     * [Required]
     * min 6 characters
     * max 6 characters
     */
    private String bankCode;

    /**
     * The country of the account, as an ISO 3166-1 alpha-2 code.
     * [Required]
     * min 2 characters
     * max 2 characters
     */
    private CountryCode country;

    /**
     * The currency of the account.
     * [Required]
     * min 3 characters
     * max 3 characters
     */
    private Currency currency;

    /**
     * The type of payment. Recurring or Regular.
     * [Required]
     */
    private BacsPaymentType paymentType;

    /**
     * Indicates whether the Bacs instrument is created when account validation returns a partial
     * match. When true, the instrument is created on a partial match; when false, instrument
     * creation fails on a partial match.
     * [Optional]
     * Default: false
     */
    private Boolean allowPartialMatch;

}
