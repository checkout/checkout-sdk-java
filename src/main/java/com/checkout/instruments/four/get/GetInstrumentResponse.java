package com.checkout.instruments.four.get;

import com.checkout.common.four.AccountHolder;
import com.checkout.common.CustomerResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class GetInstrumentResponse {

    protected String id;

    protected String fingerprint;

    // TODO Implement specific CustomerResponse domain when it's available
    protected CustomerResponse customer;

    @SerializedName("account_holder")
    protected AccountHolder accountHolder;

}