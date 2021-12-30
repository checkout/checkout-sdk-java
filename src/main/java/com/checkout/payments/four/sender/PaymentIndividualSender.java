package com.checkout.payments.four.sender;

import com.checkout.common.Address;
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
public final class PaymentIndividualSender extends PaymentSender {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    private Address address;

    private SenderIdentification identification;

    @Builder
    private PaymentIndividualSender(final String firstName,
                                    final String lastName,
                                    final Address address,
                                    final SenderIdentification identification) {
        super(RequestSenderType.INDIVIDUAL);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.identification = identification;
    }

    public PaymentIndividualSender() {
        super(RequestSenderType.INDIVIDUAL);
    }

}
