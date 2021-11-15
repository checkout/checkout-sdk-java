package com.checkout.payments.request.destination;

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
public final class PaymentRequestIdDestination extends PaymentRequestDestination {

    private String id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @Builder
    private PaymentRequestIdDestination(final String id,
                                        final String firstName,
                                        final String lastName) {
        super(PaymentDestinationType.ID);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public PaymentRequestIdDestination() {
        super(PaymentDestinationType.ID);
    }

}
