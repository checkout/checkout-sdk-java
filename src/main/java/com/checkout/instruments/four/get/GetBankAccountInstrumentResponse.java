package com.checkout.instruments.four.get;

import com.checkout.common.CountryCode;
import com.checkout.common.four.AccountType;
import com.checkout.common.four.BankDetails;
import com.checkout.common.four.Currency;
import com.checkout.common.four.InstrumentType;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class GetBankAccountInstrumentResponse extends GetInstrumentResponse {

    private final InstrumentType type = InstrumentType.BANK_ACCOUNT;

    @SerializedName("account_type")
    private AccountType accountType;

    @SerializedName("account_number")
    private String accountNumber;

    @SerializedName("bank_code")
    private String bankCode;

    @SerializedName("branch_code")
    private String branchCode;

    private String iban;

    private String bban;

    @SerializedName("swift_bic")
    private String swiftBic;

    private Currency currency;

    private CountryCode country;

    private BankDetails bank;

}
