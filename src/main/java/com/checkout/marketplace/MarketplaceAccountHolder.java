package com.checkout.marketplace;

import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.four.AccountHolderType;
import com.checkout.common.four.SenderIdentification;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class MarketplaceAccountHolder {

    private AccountHolderType type;

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

    protected MarketplaceAccountHolder(final AccountHolderType type,
                                       final String taxId,
                                       final DateOfBirth dateOfBirth,
                                       final CountryCode countryOfBirth,
                                       final String residentialStatus,
                                       final Address billingAddress,
                                       final Phone phone,
                                       final SenderIdentification identification,
                                       final String email) {
        this.type = type;
        this.taxId = taxId;
        this.dateOfBirth = dateOfBirth;
        this.countryOfBirth = countryOfBirth;
        this.residentialStatus = residentialStatus;
        this.billingAddress = billingAddress;
        this.phone = phone;
        this.identification = identification;
        this.email = email;
    }
}
