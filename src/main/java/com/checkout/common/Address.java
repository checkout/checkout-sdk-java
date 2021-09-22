package com.checkout.common;

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
public final class Address {

    @SerializedName(value = "address_line1", alternate = {"addressLine1"})
    private String addressLine1;

    @SerializedName(value = "address_line2", alternate = {"addressLine2"})
    private String addressLine2;

    private String city;

    private String state;

    private String zip;

    private CountryCode country;

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

    /**
     * @deprecated Will be removed on a future version
     */
    @Deprecated
    public String getCountry() {
        return Optional.ofNullable(country).map(CountryCode::name).orElse(null);
    }

}