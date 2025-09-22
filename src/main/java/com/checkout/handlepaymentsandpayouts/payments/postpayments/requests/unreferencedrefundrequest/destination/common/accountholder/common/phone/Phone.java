package com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.destination.common.accountholder.common.phone;

import com.checkout.common.CountryCode;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * phone
 * The account holder's phone number.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Phone {

    /**
     * The international dialing code for the account holder's address country, as an ITU-T E.164 code.
     * [Required]
     * [ 1 .. 7 ] characters
     */
    @SerializedName("country_code")
    private CountryCode countryCode;

    /**
     * The digits for the phone number, not including the country_code.
     * [Required]
     * [ 6 .. 25 ] characters
     */
    private String number;

}
