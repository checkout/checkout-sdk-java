package com.checkout.accounts;

import com.checkout.common.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Individual {

    private String firstName;

    private String middleName;

    private String lastName;

    private String tradingName;

    private String nationalTaxId;

    private Address registeredAddress;

    private DateOfBirth dateOfBirth;

    private PlaceOfBirth placeOfBirth;

    private Identification identification;
    
    private EntityFinancialDetails financialDetails;

}
