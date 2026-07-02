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

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentBankAccountRequest extends CreateInstrumentRequest {

    private AccountType accountType;

    private String accountNumber;

    private String bankCode;

    private String branchCode;

    private String iban;

    private String bban;

    private String swiftBic;

    private Currency currency;

    private CountryCode country;

    private String processingChannelId;

    private AccountHolder accountHolder;

    private BankDetails bank;

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
