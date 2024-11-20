package com.checkout.accounts;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccountPhone {

    @SerializedName("country_code")
    private CountryCode countryCode;

    private String number;

}
