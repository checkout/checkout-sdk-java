package com.checkout.payments.response.source.contexts;

import com.checkout.common.AccountHolder;
import com.checkout.common.PaymentSourceType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class AbstractPaymentContextsResponseSource {

    public PaymentSourceType type;

    @SerializedName("account_holder")
    public AccountHolder accountHolder;

}
