package com.checkout.risk;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class RiskPayment {

    private String psp;

    private String id;

}
