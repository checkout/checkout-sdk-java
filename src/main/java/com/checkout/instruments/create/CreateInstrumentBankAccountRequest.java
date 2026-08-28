package com.checkout.instruments.create;

import com.checkout.common.AccountHolder;
import com.checkout.common.AccountType;
import com.checkout.common.BankDetails;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.InstrumentType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Store bank account details.
 *
 * <p>The bank_account instrument type only supports payouts.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentBankAccountRequest extends CreateInstrumentRequest {

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
     * The ID of the primary processing channel this instrument is intended to be used for.
     * [Optional]
     */
    private String processingChannelId;

    /**
     * The account holder details.
     * [Optional]
     */
    private AccountHolder accountHolder;

    /**
     * Details of the bank.
     * [Optional]
     */
    private BankDetails bank;

    /**
     * The customer's details. Associates the instrument with an existing or new customer.
     * [Optional]
     */
    private CreateCustomerInstrumentRequest customer;

    @Builder
    private CreateInstrumentBankAccountRequest(final AccountType accountType,
                                               final String accountNumber,
                                               final String bankCode,
                                               final String branchCode,
                                               final String iban,
                                               final String bban,
                                               final String swiftBic,
                                               final Currency currency,
                                               final CountryCode country,
                                               final String processingChannelId,
                                               final AccountHolder accountHolder,
                                               final BankDetails bank,
                                               final CreateCustomerInstrumentRequest customer) {
        super(InstrumentType.BANK_ACCOUNT);
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
        this.branchCode = branchCode;
        this.iban = iban;
        this.bban = bban;
        this.swiftBic = swiftBic;
        this.currency = currency;
        this.country = country;
        this.processingChannelId = processingChannelId;
        this.accountHolder = accountHolder;
        this.bank = bank;
        this.customer = customer;
    }

    public CreateInstrumentBankAccountRequest() {
        super(InstrumentType.BANK_ACCOUNT);
    }

}
