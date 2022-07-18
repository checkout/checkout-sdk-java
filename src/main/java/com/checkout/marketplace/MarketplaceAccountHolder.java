package com.checkout.marketplace;

import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import com.checkout.common.four.AccountHolderType;
import com.checkout.common.four.SenderIdentification;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public final class MarketplaceAccountHolder {

    private AccountHolderType type;

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

    @SerializedName("residential_status")
    private String residentialStatus;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

    private SenderIdentification identification;

    private String email;

}
