package com.checkout.issuing.cardholders;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardholderRequest {

    private CardholderType type;

    private String reference;

    @SerializedName("entity_id")
    private String entityId;

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
