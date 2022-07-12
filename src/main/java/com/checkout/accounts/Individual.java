package com.checkout.accounts;

import com.checkout.common.Address;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Individual {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("middle_name")
    private String middleName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("trading_name")
    private String tradingName;

    @SerializedName("national_tax_id")
    private String nationalTaxId;

    @SerializedName("registered_address")
    private Address registeredAddress;

    @SerializedName("date_of_birth")
    private DateOfBirth dateOfBirth;

    private Identification identification;

}
