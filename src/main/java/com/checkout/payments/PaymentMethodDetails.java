package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class PaymentMethodDetails {

    private String displayName;

    private String type;

    private String network;

}
