package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.cashapp;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The customer's address from their Cash App profile.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CashAppAddress {

    /**
     * The first line of the address.
     * [Optional] readOnly
     */
    @SerializedName("address_line_1")
    private String addressLine1;

    /**
     * The second line of the address.
     * [Optional] readOnly
     */
    @SerializedName("address_line_2")
    private String addressLine2;

    /**
     * The third line of the address.
     * [Optional] readOnly
     */
    @SerializedName("address_line_3")
    private String addressLine3;

    /**
     * The address locality, such as the city or town.
     * [Optional] readOnly
     */
    private String locality;

    /**
     * The address sublocality, such as the district or neighborhood.
     * [Optional] readOnly
     */
    private String sublocality;

    /**
     * The address's top-level administrative district, such as the state or province.
     * [Optional] readOnly
     */
    @SerializedName("administrative_district_level_1")
    private String administrativeDistrictLevel1;

    /**
     * The postal or zip code.
     * [Optional] readOnly
     */
    @SerializedName("postal_code")
    private String postalCode;

    /**
     * The address country, in ISO 3166-1 alpha-2 format. Max 2 characters.
     * [Optional] readOnly
     */
    private CountryCode country;
}
