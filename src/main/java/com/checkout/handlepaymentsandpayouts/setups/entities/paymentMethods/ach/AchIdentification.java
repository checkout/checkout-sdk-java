package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.ach;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The account holder's government document identification.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AchIdentification {

    /**
     * The document type.
     * [Optional]
     */
    private String type;

    /**
     * The country where the document was issued.
     * [Optional]
     */
    @SerializedName("issuing_country")
    private String issuingCountry;

    /**
     * The document number.
     * [Optional]
     */
    private String number;
}
