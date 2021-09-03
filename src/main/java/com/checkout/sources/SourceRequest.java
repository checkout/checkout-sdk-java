package com.checkout.sources;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.checkout.common.CheckoutUtils.validateParams;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SourceRequest {

    private String type;
    private Address billingAddress;
    private String reference;
    private Phone phone;
    private CustomerRequest customer;
    private SourceData sourceData;

    public SourceRequest(final String type, final Address billingAddress) {
        validateParams("type", type, "billingAddress", billingAddress);
        this.type = type;
        this.billingAddress = billingAddress;
    }

}
