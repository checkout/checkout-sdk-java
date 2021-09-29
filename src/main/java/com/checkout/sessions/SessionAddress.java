package com.checkout.sessions;

import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class SessionAddress extends Address {

    @SerializedName(value = "address_line3", alternate = {"addressLine3"})
    private String addressLine3;

    @Builder(builderMethodName = "builderSessionAddress")
    public SessionAddress(final String addressLine1,
                          final String addressLine2,
                          final String addressLine3,
                          final String city,
                          final String state,
                          final String zip,
                          final CountryCode country) {
        super(addressLine1, addressLine2, city, state, zip, country);
        this.addressLine3 = addressLine3;
    }

}
