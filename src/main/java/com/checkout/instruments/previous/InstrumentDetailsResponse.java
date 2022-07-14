package com.checkout.instruments.previous;

import com.checkout.instruments.get.InstrumentCustomerResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class InstrumentDetailsResponse extends InstrumentDetails {

    private InstrumentCustomerResponse customer;

}
