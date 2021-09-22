package com.checkout.risk.source;

import com.checkout.common.Address;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardSourcePrism extends RiskPaymentRequestSource {

    private String number;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String name;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

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

    public CardSourcePrism() {
        super(PaymentSourceType.CARD);
    }

}
