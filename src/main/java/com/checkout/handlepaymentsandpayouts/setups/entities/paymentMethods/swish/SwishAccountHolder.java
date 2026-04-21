package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.swish;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Swish account holder's details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SwishAccountHolder {

    /**
     * The account holder's first name.
     * [Optional]
     */
    @SerializedName("first_name")
    private String firstName;

    /**
     * The account holder's last name.
     * [Optional]
     */
    @SerializedName("last_name")
    private String lastName;
}
