package com.checkout.accounts;

import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.AccountHolderType;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class AccountsAccountHolder {

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

    private AccountPhone phone;

    private AccountHolderIdentification identification;

    private String email;

    protected AccountsAccountHolder(final AccountHolderType type,
                                    final String taxId,
                                    final DateOfBirth dateOfBirth,
                                    final CountryCode countryOfBirth,
                                    final String residentialStatus,
                                    final Address billingAddress,
                                    final AccountPhone phone,
                                    final AccountHolderIdentification identification,
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
