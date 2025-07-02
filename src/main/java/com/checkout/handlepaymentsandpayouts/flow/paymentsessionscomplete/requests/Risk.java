package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Risk {

    /**
     * Default: true Specifies whether to perform a risk assessment.
     */
    @Builder.Default
    private Boolean enabled = true;

}
