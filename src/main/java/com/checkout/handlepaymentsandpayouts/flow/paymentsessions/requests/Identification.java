package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.IdentificationType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Identification {

    /**
     * The type of identification used to identify the sender
     */
    private IdentificationType type;

    /**
     * The identification number
     */
    private String number;

    /**
     * The two-letter ISO country code of the country that issued the identification
     */
    @SerializedName("issuing_country")
    private String issuingCountry;

}
