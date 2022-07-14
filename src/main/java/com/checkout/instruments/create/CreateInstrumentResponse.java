package com.checkout.instruments.create;

import com.checkout.HttpMetadata;
import com.checkout.common.CustomerResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class CreateInstrumentResponse extends HttpMetadata {

    protected String id;

    protected String fingerprint;

    @SerializedName("customer")
    protected CustomerResponse customer;

}
