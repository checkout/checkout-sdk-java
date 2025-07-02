package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

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
public final class Address {

    /**
     * The first line of the address.
     */
    @SerializedName("address_line1")
    private String addressLine1;

    /**
     * The second line of the address
     */
    @SerializedName("address_line2")
    private String addressLine2;

    /**
     * The address city.
     */
    private String city;

    /**
     * The state or province of the address country ISO 3166-2 code (for example: CA for California in the United
     * States).
     */
    private String state;

    /**
     * The address zip or postal code.
     */
    private String zip;

    /**
     * The two-letter ISO country code of the address.
     */
    private CountryCode country;

}
