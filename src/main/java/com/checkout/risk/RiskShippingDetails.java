package com.checkout.risk;

import com.checkout.common.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class RiskShippingDetails {

    private Address address;

}
