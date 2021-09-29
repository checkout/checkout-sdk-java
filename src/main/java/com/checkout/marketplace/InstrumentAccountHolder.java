package com.checkout.marketplace;

import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class InstrumentAccountHolder {

    private InstrumentAccountHolderType type;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("tax_id")
    private String taxId;

    @SerializedName("date_of_birth")
    private DateOfBirth dateOfBirth;

    @SerializedName("country_of_birth")
    private CountryCode countryOfBirth;

    private String residentialStatus;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

    private Identification identification;

    private String email;

}
