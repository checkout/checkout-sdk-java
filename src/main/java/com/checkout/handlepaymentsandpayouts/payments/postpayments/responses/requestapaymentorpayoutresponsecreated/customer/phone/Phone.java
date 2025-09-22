package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.customer.phone;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * phone
 * The customer's phone number.
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
    private CountryCode countryCode;

    /**
     * The phone number. Required if source.type is tamara.
     * [Optional]
     * [ 6 .. 25 ] characters
     */
    private String number;

}
