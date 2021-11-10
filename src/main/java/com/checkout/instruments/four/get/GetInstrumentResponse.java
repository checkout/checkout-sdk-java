package com.checkout.instruments.four.get;

import com.checkout.common.four.AccountHolder;
import com.checkout.instruments.InstrumentCustomerResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class GetInstrumentResponse {

    protected String id;

    protected String fingerprint;

    protected InstrumentCustomerResponse customer;

    @SerializedName("account_holder")
    protected AccountHolder accountHolder;

}
