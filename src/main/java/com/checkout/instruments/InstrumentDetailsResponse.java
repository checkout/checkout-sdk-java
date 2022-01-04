package com.checkout.instruments;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class InstrumentDetailsResponse extends InstrumentDetails {

    private InstrumentCustomerResponse customer;

}
