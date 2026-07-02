package com.checkout.accounts;

import com.checkout.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Company {

    private String legalName;

    private String tradingName;

    private String businessRegistrationNumber;

    private DateOfIncorporation dateOfIncorporation;

    private String regulatoryLicenceNumber;

    private Address principalAddress;

    private Address registeredAddress;

    private List<Representative> representatives;

    private EntityDocument document;

    private EntityFinancialDetails financialDetails;

    private BusinessType businessType;

}
