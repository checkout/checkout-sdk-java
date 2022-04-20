package com.checkout.instruments.four.create;

import com.checkout.common.CustomerResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class CreateInstrumentResponse {

    protected String id;

    protected String fingerprint;

    @SerializedName("customer")
    protected CustomerResponse customer;

}
