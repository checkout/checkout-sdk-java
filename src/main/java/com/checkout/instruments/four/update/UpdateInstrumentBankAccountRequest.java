package com.checkout.instruments.four.update;

import com.checkout.common.CountryCode;
import com.checkout.common.four.AccountHolder;
import com.checkout.common.four.AccountType;
import com.checkout.common.four.BankDetails;
import com.checkout.common.four.Currency;
import com.checkout.common.four.InstrumentType;
import com.checkout.common.four.UpdateCustomerRequest;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateInstrumentBankAccountRequest extends UpdateInstrumentRequest {

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

    private final CountryCode country;

    @SerializedName("processing_channel_id")
    private final String processingChannelId;

    @SerializedName("account_holder")
    private final AccountHolder accountHolder;

    private final BankDetails bank;

    private final UpdateCustomerRequest customer;

    @Builder
    protected UpdateInstrumentBankAccountRequest(final AccountType accountType,
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

}
