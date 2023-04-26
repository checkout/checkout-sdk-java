package com.checkout.issuing.cardholders;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CardholderDetailsResponse extends Resource {
    
    private String id;

    private CardholderType type;

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

    private String reference;

    @SerializedName("account_entity_id")
    private String accountEntityId;

    @SerializedName("parent_sub_entity_id")
    private String parentSubEntityId;

    @SerializedName("entity_id")
    private String entityId;

    @SerializedName("created_date")
    private Instant createdDate;

    @SerializedName("last_modified_date")
    private Instant lastModifiedDate;
}
