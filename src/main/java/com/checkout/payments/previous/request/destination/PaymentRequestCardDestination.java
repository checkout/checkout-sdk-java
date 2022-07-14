package com.checkout.payments.previous.request.destination;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.checkout.payments.PaymentDestinationType;
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
public final class PaymentRequestCardDestination extends PaymentRequestDestination {

    private String number;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    private String name;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

    @Builder
    private PaymentRequestCardDestination(final String number,
                                          final Integer expiryMonth,
                                          final Integer expiryYear,
                                          final String firstName,
                                          final String lastName,
                                          final String name,
                                          final Address billingAddress,
                                          final Phone phone) {
        super(PaymentDestinationType.CARD);
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = name;
        this.billingAddress = billingAddress;
        this.phone = phone;
    }

    public PaymentRequestCardDestination() {
        super(PaymentDestinationType.CARD);
    }

}
