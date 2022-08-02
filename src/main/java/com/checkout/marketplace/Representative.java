package com.checkout.marketplace;

import com.checkout.common.Address;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public final class Representative {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("middle_name")
    private String middleName;

    @SerializedName("last_name")
    private String lastName;

    private Address address;

    private Identification identification;

    private Phone phone;

    @SerializedName("date_of_birth")
    private DateOfBirth dateOfBirth;

    @SerializedName("place_of_birth")
    private PlaceOfBirth placeOfBirth;

    private List<EntityRoles> roles;

}
