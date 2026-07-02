package com.checkout.instruments.get;

import com.checkout.HttpMetadata;
import com.checkout.common.AccountHolder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class GetInstrumentResponse extends HttpMetadata {

    protected String id;

    protected String fingerprint;

    protected InstrumentCustomerResponse customer;

    protected AccountHolder accountHolder;

}
