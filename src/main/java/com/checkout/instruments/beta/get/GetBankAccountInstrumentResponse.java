package com.checkout.instruments.beta.get;

import com.checkout.common.beta.AccountType;
import com.checkout.common.beta.BankDetails;
import com.checkout.common.beta.Currency;
import com.checkout.common.beta.InstrumentType;
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

    private String country;

    private BankDetails bank;

}
