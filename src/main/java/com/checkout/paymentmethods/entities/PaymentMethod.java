package com.checkout.paymentmethods.entities;

import com.checkout.common.PaymentMethodType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public final class PaymentMethod {

    private PaymentMethodType type;

    private String name;

    /**
     * The unique identifier for the merchant, provided by the partner.
     */
    private String partnerMerchantId;
}