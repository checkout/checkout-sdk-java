package com.checkout.payments.response.destination;

import com.checkout.common.AccountHolder;
import com.checkout.common.AccountType;
import com.checkout.common.BankDetails;
import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PaymentResponseBankAccountDestination extends AbstractPaymentResponseDestination implements PaymentResponseDestination {

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

}
