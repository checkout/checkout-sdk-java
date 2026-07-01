package com.checkout.instruments.get;

import com.checkout.common.AccountType;
import com.checkout.common.BankDetails;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.InstrumentType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetBankAccountInstrumentResponse extends GetInstrumentResponse {

    private final InstrumentType type = InstrumentType.BANK_ACCOUNT;

    private AccountType accountType;

    private String accountNumber;

    private String bankCode;

    private String branchCode;

    private String iban;

    private String bban;

    private String swiftBic;

    private Currency currency;

    private CountryCode country;

    private BankDetails bank;

}
