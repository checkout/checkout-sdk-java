package com.checkout.accounts;

import com.checkout.common.Address;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public final class Company {

    @SerializedName("legal_name")
    private String legalName;

    @SerializedName("trading_name")
    private String tradingName;

    @SerializedName("business_registration_number")
    private String businessRegistrationNumber;

    @SerializedName("date_of_incorporation")
    private DateOfIncorporation dateOfIncorporation;

    @SerializedName("regulatory_licence_number")
    private String regulatoryLicenceNumber;

    @SerializedName("principal_address")
    private Address principalAddress;

    @SerializedName("registered_address")
    private Address registeredAddress;

    private EntityDocument document;

    private List<Representative> representatives;

    @SerializedName("financial_details")
    private EntityFinancialDetails financialDetails;

    @SerializedName("business_type")
    private BusinessType businessType;

}
