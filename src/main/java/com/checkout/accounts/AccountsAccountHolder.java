package com.checkout.accounts;

import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.AccountHolderType;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import lombok.Data;

@Data
public abstract class AccountsAccountHolder {

    private AccountHolderType type;

    private String taxId;

    private DateOfBirth dateOfBirth;

    private CountryCode countryOfBirth;

    private String residentialStatus;

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
