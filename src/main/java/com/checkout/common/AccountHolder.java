package com.checkout.common;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class AccountHolder extends AccountHolderBase {

    private Boolean accountNameInquiry;

    @Builder
    private AccountHolder(
            final AccountHolderType type,
            final String fullName,
            final String firstName,
            final String middleName,
            final String lastName,
            final String email,
            final String gender,
            final String companyName,
            final String taxId,
            final String dateOfBirth,
            final CountryCode countryOfBirth,
            final String residentialStatus,
            final Address billingAddress,
            final Phone phone,
            final AccountHolderIdentification identification,
            final Boolean accountNameInquiry
    ) {
        super(type, fullName, firstName, middleName, lastName, email, gender, companyName, taxId, dateOfBirth, countryOfBirth, residentialStatus, billingAddress, phone, identification);
        this.accountNameInquiry = accountNameInquiry;
    }

}
