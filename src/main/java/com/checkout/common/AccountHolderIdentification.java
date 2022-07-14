package com.checkout.common;

import com.google.gson.annotations.SerializedName;
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

    @SerializedName("issuing_country")
    private CountryCode issuingCountry;

    @SerializedName("date_of_expiry")
    private String dateOfExpiry;

}
