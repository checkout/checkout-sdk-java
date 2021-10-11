package com.checkout.payments.apm;

import com.checkout.common.PaymentSourceType;
import com.checkout.payments.RequestSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class GiropaySource implements RequestSource {

    private final PaymentSourceType type = PaymentSourceType.GIROPAY;

    /**
     * @deprecated BIC doesn't need to be supplied anymore. This attribute will be removed in a future version.
     */
    @Deprecated
    private String bic;

    private String purpose;

}