package com.checkout.instruments.four.get;

import com.checkout.common.four.AccountHolderType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class BankAccountFieldQuery {

    @SerializedName("account-holder-type")
    private AccountHolderType accountHolderType;

    @SerializedName("payment-network")
    private PaymentNetwork paymentNetwork;

}
