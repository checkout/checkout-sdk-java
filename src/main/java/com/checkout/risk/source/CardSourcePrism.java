package com.checkout.risk.source;

import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardSourcePrism extends RiskPaymentRequestSource {

    private final String number;

    @SerializedName("expiry_month")
    private final Integer expiryMonth;

    @SerializedName("expiry_year")
    private final Integer expiryYear;

    private final String name;

    @SerializedName("billing_address")
    private final Address billingAddress;

    private final Phone phone;

    @Builder
    protected CardSourcePrism(final String number,
                              final Integer expiryMonth,
                              final Integer expiryYear,
                              final String name,
                              final Address billingAddress,
                              final Phone phone) {
        super(PaymentSourceType.CARD);
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.name = name;
        this.billingAddress = billingAddress;
        this.phone = phone;
    }

}
