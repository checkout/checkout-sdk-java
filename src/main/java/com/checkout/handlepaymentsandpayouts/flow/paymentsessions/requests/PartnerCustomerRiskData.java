package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PartnerCustomerRiskData {

    /**
     * The key for the pair.
     */
    private String key;

    /**
     * The value for the pair.
     */
    private String value;

}
