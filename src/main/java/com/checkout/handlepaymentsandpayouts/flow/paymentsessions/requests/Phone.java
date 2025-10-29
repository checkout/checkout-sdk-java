package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Phone {

    /**
     * The international country calling code.
     */
    @SerializedName("country_code")
    private String countryCode;

    /**
     * The phone number.
     */
    private String number;

}
