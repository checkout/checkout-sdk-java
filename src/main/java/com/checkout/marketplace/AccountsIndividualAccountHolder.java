package com.checkout.marketplace;

import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.four.AccountHolderType;
import com.checkout.common.four.SenderIdentification;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AccountsIndividualAccountHolder extends MarketplaceAccountHolder {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @Builder
    private AccountsIndividualAccountHolder(final AccountHolderType type,
                                            final String taxId,
                                            final DateOfBirth dateOfBirth,
                                            final CountryCode countryOfBirth,
                                            final String residentialStatus,
                                            final Address billingAddress,
                                            final Phone phone,
                                            final SenderIdentification identification,
                                            final String email,
                                            final String firstName,
                                            final String lastName) {
        super(type, taxId, dateOfBirth, countryOfBirth, residentialStatus, billingAddress, phone, identification, email);
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
