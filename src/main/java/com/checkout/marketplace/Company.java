package com.checkout.marketplace;

import com.checkout.common.Address;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public final class Company {

    @SerializedName("business_registration_number")
    private String businessRegistrationNumber;

    @SerializedName("business_type")
    private BusinessType businessType;

    @SerializedName("legal_name")
    private String legalName;

    @SerializedName("trading_name")
    private String tradingName;

    @SerializedName("principal_address")
    private Address principalAddress;

    @SerializedName("registered_address")
    private Address registeredAddress;

    private EntityDocument document;

    private List<Representative> representatives;

    @SerializedName("financial_details")
    private EntityFinancialDetails financialDetails;

}
