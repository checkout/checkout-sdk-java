package com.checkout.payments.four.request.destination;

import com.checkout.common.CountryCode;
import com.checkout.common.four.AccountHolder;
import com.checkout.common.four.AccountType;
import com.checkout.common.four.BankDetails;
import com.checkout.payments.PaymentDestinationType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentRequestBankAccountDestination extends PaymentRequestDestination {

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

    private CountryCode country;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    private BankDetails bank;

    @Builder
    private PaymentRequestBankAccountDestination(final AccountType accountType,
                                                 final String accountNumber,
                                                 final String bankCode,
                                                 final String branchCode,
                                                 final String iban,
                                                 final String bban,
                                                 final String swiftBic,
                                                 final CountryCode country,
                                                 final AccountHolder accountHolder,
                                                 final BankDetails bank) {
        super(PaymentDestinationType.BANK_ACCOUNT);
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
        this.branchCode = branchCode;
        this.iban = iban;
        this.bban = bban;
        this.swiftBic = swiftBic;
        this.country = country;
        this.accountHolder = accountHolder;
        this.bank = bank;
    }

    public PaymentRequestBankAccountDestination() {
        super(PaymentDestinationType.BANK_ACCOUNT);
    }

}
