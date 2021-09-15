package com.checkout.payments.four.sender;

import com.checkout.common.Address;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RequestIndividualSender extends RequestSender {

    @SerializedName("first_name")
    private final String firstName;

    @SerializedName("last_name")
    private final String lastName;

    private final Address address;

    @Builder
    private RequestIndividualSender(final String firstName,
                                    final String lastName,
                                    final Address address) {
        super(RequestSenderType.INDIVIDUAL);
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

}
