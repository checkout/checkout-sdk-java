package com.checkout.instruments.update;

import com.checkout.common.AccountHolder;
import com.checkout.common.AccountType;
import com.checkout.common.BankDetails;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.InstrumentType;
import com.checkout.common.UpdateCustomerRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentBankAccountRequest extends UpdateInstrumentRequest {

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

    private UpdateCustomerRequest customer;

    @Builder
    private UpdateInstrumentBankAccountRequest(final AccountType accountType,
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
                                               final UpdateCustomerRequest customer) {
        super(InstrumentType.TOKEN);
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

    public UpdateInstrumentBankAccountRequest() {
        super(InstrumentType.TOKEN);
    }

}
