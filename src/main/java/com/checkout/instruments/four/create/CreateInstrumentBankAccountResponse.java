package com.checkout.instruments.four.create;

import com.checkout.common.CustomerResponse;
import com.checkout.common.four.BankDetails;
import com.checkout.common.four.InstrumentType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentBankAccountResponse extends CreateInstrumentResponse {

    private final InstrumentType type = InstrumentType.BANK_ACCOUNT;

    private String fingerprint;

    private CustomerResponse customerResponse;

    private BankDetails bank;

    @SerializedName("swift_bic")
    private String swiftBic;

    @SerializedName("account_number")
    private String accountNumber;

    @SerializedName("bank_code")
    private String bankCode;

    @SerializedName("iban")
    private String iban;

}
