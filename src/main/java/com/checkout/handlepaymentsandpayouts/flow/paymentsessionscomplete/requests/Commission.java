package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Commission {

    /**
     * An optional commission to collect, as a fixed amount in minor currency units.
     */
    private Long amount;

    /**
     * An optional commission to collect, as a percentage value with up to eight decimal places.
     */
    private Double percentage;

}
