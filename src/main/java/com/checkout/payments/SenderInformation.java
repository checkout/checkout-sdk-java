package com.checkout.payments;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SenderInformation {

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    private String address;

    private String city;

    @SerializedName("postalCode")
    private String postalCode;

    private String state;

    private CountryCode country;

    @SerializedName("sourceOfFunds")
    private String sourceOfFunds;

    @SerializedName("accountNumber")
    private String accountNumber;

    private String reference;

    /**
     * @deprecated Will be removed on a future version
     */
    @Deprecated
    public String getCountry() {
        return Optional.ofNullable(country).map(CountryCode::name).orElse(null);
    }

    /**
     * @deprecated Will be removed on a future version
     */
    @Deprecated
    public void setCountry(final String country) {
        this.country = CountryCode.fromString(country);
    }

    public void setCountry(final CountryCode country) {
        this.country = country;
    }

}
