package com.checkout.handlepaymentsandpayouts.payments.common.source.paymentgetresponseklarnasourcesource.accountholder.phone;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * phone
 * Phone number of the account holder.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Phone {

    /**
     * [Optional]
     */
    @SerializedName("country_code")
    private String countryCode;

    /**
     * [Optional]
     */
    private String number;

}
