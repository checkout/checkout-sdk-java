package com.checkout.marketplace;

import com.checkout.common.CountryCode;
import com.checkout.common.four.AccountHolderType;
import com.checkout.common.four.AccountHolder;
import com.checkout.common.four.SenderIdentification;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MarketplaceAccountHolder extends AccountHolder {

    private AccountHolderType type;

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

    private SenderIdentification identification;

    private String email;

}
