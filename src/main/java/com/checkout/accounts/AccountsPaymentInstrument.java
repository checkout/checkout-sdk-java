package com.checkout.accounts;

import com.checkout.common.AccountType;
import com.checkout.common.BankDetails;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.InstrumentType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class AccountsPaymentInstrument {

    private final InstrumentType type = InstrumentType.BANK_ACCOUNT;

    private String label;

    private AccountType accountType;

    private String accountNumber;

    private String bankCode;

    private String branchCode;

    private String iban;

    private String bban;

    private String swiftBic;

    private Currency currency;

    private CountryCode country;

    private InstrumentDocument document;

    private AccountsAccountHolder accountHolder;

    private BankDetails bank;

}
