package com.checkout.payments.request.source;

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
public final class RequestDLocalSource extends AbstractRequestSource {

    private String number;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    private String name;

    private String cvv;

    private Boolean stored;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

    @Builder
    private RequestDLocalSource(final String number,
                                final Integer expiryMonth,
                                final Integer expiryYear,
                                final String name,
                                final String cvv,
                                final Boolean stored,
                                final Address billingAddress,
                                final Phone phone) {
        super(PaymentSourceType.DLOCAL);
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.name = name;
        this.cvv = cvv;
        this.stored = stored;
        this.billingAddress = billingAddress;
        this.phone = phone;
    }

    public RequestDLocalSource() {
        super(PaymentSourceType.DLOCAL);
    }

}
