package com.checkout.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolderBase {

    private AccountHolderType type;

    private String fullName;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String gender;

    private String companyName;

    private String taxId;

    private String dateOfBirth;

    private CountryCode countryOfBirth;

    private String residentialStatus;

    private Address billingAddress;

    private Phone phone;

    private AccountHolderIdentification identification;

}
