package com.checkout.payments.four.request.source;

import com.checkout.common.CountryCode;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.four.AccountHolder;
import com.checkout.common.four.AccountType;
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
public class RequestBankAccountSource extends AbstractRequestSource {

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("account_type")
    private AccountType accountType;

    private CountryCode country;

    @SerializedName("account_number")
    private String accountNumber;

    @SerializedName("bank_code")
    private String bankCode;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    public RequestBankAccountSource() {
        super(PaymentSourceType.BANK_ACCOUNT);
    }

    @Builder
    public RequestBankAccountSource(final String paymentMethod,
                                    final AccountType accountType,
                                    final CountryCode country,
                                    final String accountNumber,
                                    final String bankCode,
                                    final AccountHolder accountHolder) {
        super(PaymentSourceType.BANK_ACCOUNT);
        this.paymentMethod = paymentMethod;
        this.accountType = accountType;
        this.country = country;
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
        this.accountHolder = accountHolder;
    }
}
