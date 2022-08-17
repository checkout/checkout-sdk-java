package com.checkout.payments.source;

import com.checkout.common.AccountHolder;
import com.checkout.common.AccountType;
import com.checkout.payments.RequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountSource implements RequestSource {
    public static final String TYPE_NAME = "bank_account";

    private static final String type = TYPE_NAME;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("account_type")
    private AccountType accountType;

    private String country;

    @SerializedName("account_number")
    private String accountNumber;

    @SerializedName("bank_code")
    private String bankCode;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Override
    public String getType() {
        return TYPE_NAME;
    }
}
