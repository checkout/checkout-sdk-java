package com.checkout.handlepaymentsandpayouts.payments.common.source.cardsource.phone;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * phone
 * The payment source owner's phone number
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Phone {

    /**
     * The international country calling code. Required if source.type is tamara.
     * [Optional]
     * [ 1 .. 7 ] characters
     */
    @SerializedName("country_code")
    private String countryCode;

    /**
     * The phone number. Required if source.type is tamara.
     * [Optional]
     * [ 6 .. 25 ] characters
     */
    private String number;

}
