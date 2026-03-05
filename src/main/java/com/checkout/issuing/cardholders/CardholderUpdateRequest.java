package com.checkout.issuing.cardholders;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class CardholderUpdateRequest {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("middle_name")
    private String middleName;

    @SerializedName("last_name")
    private String lastName;

    private String email;

    @SerializedName("phone_number")
    private Phone phoneNumber;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("billing_address")
    private Address billingAddress;

    @SerializedName("residency_address")
    private Address residencyAddress;

    private CardholderDocument document;
}
