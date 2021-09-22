package com.checkout.payments.four.request.source;

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
public final class RequestCardSource extends RequestSource {

    private String number;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String name;

    private Integer ccv;

    private boolean stored;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

    @Builder
    private RequestCardSource(final String number,
                              final Integer expiryMonth,
                              final Integer expiryYear,
                              final String name,
                              final Integer ccv,
                              final boolean stored,
                              final Address billingAddress,
                              final Phone phone) {
        super(PaymentSourceType.CARD);
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.name = name;
        this.ccv = ccv;
        this.stored = stored;
        this.billingAddress = billingAddress;
        this.phone = phone;
    }

    public RequestCardSource() {
        super(PaymentSourceType.CARD);
    }

}
