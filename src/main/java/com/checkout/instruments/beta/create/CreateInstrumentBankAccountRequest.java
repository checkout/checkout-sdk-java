package com.checkout.instruments.beta.create;

import com.checkout.common.beta.AccountHolder;
import com.checkout.common.beta.AccountType;
import com.checkout.common.beta.BankDetails;
import com.checkout.common.beta.Currency;
import com.checkout.common.beta.InstrumentType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreateInstrumentBankAccountRequest extends CreateInstrumentRequest {

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

    @SerializedName("processing_channel_id")
    private final String processingChannelId;

    @SerializedName("account_holder")
    private final AccountHolder accountHolder;

    private final BankDetails bank;

    private final CreateCustomerInstrumentRequest customer;

    @Builder
    protected CreateInstrumentBankAccountRequest(final AccountType accountType,
                                                 final String accountNumber,
                                                 final String bankCode,
                                                 final String branchCode,
                                                 final String iban,
                                                 final String bban,
                                                 final String swiftBic,
                                                 final Currency currency,
                                                 final String country,
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

}
