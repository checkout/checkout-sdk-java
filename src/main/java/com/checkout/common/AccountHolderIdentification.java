package com.checkout.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class AccountHolderIdentification {

    private AccountHolderIdentificationType type;

    private String number;

    private CountryCode issuingCountry;

    private String dateOfExpiry;

}
