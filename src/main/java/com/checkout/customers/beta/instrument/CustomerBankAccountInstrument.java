package com.checkout.customers.beta.instrument;

import com.checkout.common.beta.BankDetails;
import com.checkout.common.beta.Currency;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CustomerBankAccountInstrument extends CustomerInstrument {

    private final String id;

    private final String fingerprint;

    @SerializedName("account_type")
    private final AccountType accountType;

    @SerializedName("account_number")
    private final String accountNumber;

    @SerializedName("bank_code")
    private final String bankCode;

    @SerializedName("branch_code")
    private final String branchCode;

    private final String iban;

    private final String bban;

    @SerializedName("swift_bic")
    private final String swiftBic;

    private final Currency currency;

    private final String country;

    @SerializedName("account_holder")
    private final AccountHolder accountHolder;

    private final BankDetails bank;

}
