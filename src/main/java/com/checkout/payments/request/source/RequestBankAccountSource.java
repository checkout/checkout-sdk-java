package com.checkout.payments.request.source;

import com.checkout.common.AccountHolder;
import com.checkout.common.AccountType;
import com.checkout.common.CountryCode;
import com.checkout.common.PaymentSourceType;
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
public final class RequestBankAccountSource extends AbstractRequestSource {

    /**
     * The payment method to use. For example, "ach".
     * [Optional]
     */
    @SerializedName("payment_method")
    private String paymentMethod;

    /**
     * The type of account.
     * [Optional]
     */
    @SerializedName("account_type")
    private AccountType accountType;

    /**
     * The two-letter ISO country code of the bank account.
     * [Optional]
     */
    private CountryCode country;

    /**
     * The account number.
     * [Optional]
     */
    @SerializedName("account_number")
    private String accountNumber;

    /**
     * The bank routing code.
     * [Optional]
     */
    @SerializedName("bank_code")
    private String bankCode;

    /**
     * The account holder's details.
     * [Optional]
     */
    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    public RequestBankAccountSource() {
        super(PaymentSourceType.BANK_ACCOUNT);
    }

    @Builder
    private RequestBankAccountSource(final String paymentMethod,
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
