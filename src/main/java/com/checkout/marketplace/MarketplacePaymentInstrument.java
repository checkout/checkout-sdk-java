package com.checkout.marketplace;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.InstrumentType;
import com.checkout.common.four.AccountType;
import com.checkout.common.four.BankDetails;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class MarketplacePaymentInstrument {

    private final InstrumentType type = InstrumentType.BANK_ACCOUNT;

    private String label;

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

    private InstrumentDocument document;

    private BankDetails bank;

}
