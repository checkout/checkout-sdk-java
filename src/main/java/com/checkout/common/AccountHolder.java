package com.checkout.common;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class AccountHolder {

    private AccountHolderType type;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("middle_name")
    private String middleName;

    @SerializedName("last_name")
    private String lastName;

    private String email;

    private String gender;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("tax_id")
    private String taxId;

    @SerializedName("date_of_birth")
    private String dateOfBirth;

    @SerializedName("country_of_birth")
    private CountryCode countryOfBirth;

    @SerializedName("residential_status")
    private String residentialStatus;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

    private AccountHolderIdentification identification;

    private Boolean accountNameInquiry;

}
