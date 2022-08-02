package com.checkout.accounts;

import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.AccountHolderType;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AccountsCorporateAccountHolder extends AccountsAccountHolder {

    @SerializedName("company_name")
    private String companyName;

    @Builder
    private AccountsCorporateAccountHolder(final AccountHolderType type,
                                           final String taxId,
                                           final DateOfBirth dateOfBirth,
                                           final CountryCode countryOfBirth,
                                           final String residentialStatus,
                                           final Address billingAddress,
                                           final AccountPhone phone,
                                           final AccountHolderIdentification identification,
                                           final String email,
                                           final String companyName) {
        super(type, taxId, dateOfBirth, countryOfBirth, residentialStatus, billingAddress, phone, identification, email);
        this.companyName = companyName;
    }
}
