package com.checkout.payments.request.destination;

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
public final class PaymentRequestTokenDestination extends PaymentRequestDestination {

    private String token;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

    @Builder
    private PaymentRequestTokenDestination(final String token,
                                           final String firstName,
                                           final String lastName,
                                           final Address billingAddress,
                                           final Phone phone) {
        super(PaymentDestinationType.TOKEN);
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.billingAddress = billingAddress;
        this.phone = phone;
    }

    public PaymentRequestTokenDestination() {
        super(PaymentDestinationType.TOKEN);
    }

}

