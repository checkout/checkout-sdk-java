package com.checkout.instruments.get;

import com.checkout.common.AccountHolder;
import com.checkout.common.AccountType;
import com.checkout.common.BankDetails;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Bank account details.
 *
 * <p>The id and the fingerprint are inherited from {@link GetInstrumentResponse}. This is the only
 * retrieve variant whose account holder is the shared {@link AccountHolder} type; the card, SEPA,
 * ACH and Bacs Direct Debit variants each declare their own shape.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetBankAccountInstrumentResponse extends GetInstrumentResponse {

    /**
     * The type of instrument.
     * [Required]
     */
    private final InstrumentType type = InstrumentType.BANK_ACCOUNT;

    /**
     * The type of account.
     * [Optional]
     * Enum: "savings" "current" "cash"
     */
    private AccountType accountType;

    /**
     * Number (which can contain letters) that identifies the account.
     * [Optional]
     */
    private String accountNumber;

    /**
     * Code that identifies the bank.
     * [Optional]
     */
    private String bankCode;

    /**
     * Code that identifies the bank branch.
     * [Optional]
     */
    private String branchCode;

    /**
     * Internationally agreed standard for identifying bank account.
     * [Optional]
     */
    private String iban;

    /**
     * The combination of bank code and/or branch code and account number.
     * [Optional]
     */
    private String bban;

    /**
     * 8 or 11 character code which identifies the bank or bank branch.
     * [Optional]
     */
    private String swiftBic;

    /**
     * The three-letter ISO currency code of the account's currency.
     * [Required]
     */
    private Currency currency;

    /**
     * The two-letter ISO country code of where the account is based.
     * [Required]
     */
    private CountryCode country;

    /**
     * Details of the bank.
     * [Optional]
     */
    private BankDetails bank;

    /**
     * The account holder details.
     * [Optional]
     */
    private AccountHolder accountHolder;

}
