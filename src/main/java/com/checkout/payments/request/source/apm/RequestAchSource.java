package com.checkout.payments.request.source.apm;

import com.checkout.common.AccountHolder;
import com.checkout.common.AccountType;
import com.checkout.common.CountryCode;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.request.source.AbstractRequestSource;
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
public final class RequestAchSource extends AbstractRequestSource {

    @SerializedName("account_type")
    private AccountType accountType;

    @SerializedName("country")
    private CountryCode country;

    @SerializedName("account_number")
    private String accountNumber;

    @SerializedName("bank_code")
    private String bankCode;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Builder
    private RequestAchSource(
            final AccountType accountType,
            final CountryCode country,
            final String accountNumber,
            final String bankCode,
            final AccountHolder accountHolder) {
        super(PaymentSourceType.ACH);
        this.accountType = accountType;
        this.country = country;
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
        this.accountHolder = accountHolder;
    }

    public RequestAchSource() {
        super(PaymentSourceType.ACH);
    }
}