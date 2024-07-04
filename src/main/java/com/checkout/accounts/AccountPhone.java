package com.checkout.accounts;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class AccountPhone {

    @SerializedName("country_code")
    private CountryCode countryCode;

    private String number;

}
