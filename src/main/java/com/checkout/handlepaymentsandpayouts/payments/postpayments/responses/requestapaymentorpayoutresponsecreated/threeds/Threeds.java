package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.threeds;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 3ds
 * Provides 3D Secure enrollment status if the payment was downgraded to non-3D Secure
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Threeds {

    /**
     * Indicates whether this was a 3D Secure payment downgraded to non-3D-Secure (when attempt_n3d is specified)
     * [Optional]
     */
    private Boolean downgraded;

    /**
     * Indicates the 3D Secure enrollment status of the issuer
     * Y - Issuer enrolled
     * N - Customer not enrolled
     * U - Unknown
     * [Optional]
     */
    private String enrolled;

    /**
     * Indicates the reason why the payment was upgraded to 3D Secure.
     * [Optional]
     */
    @SerializedName("upgrade_reason")
    private String upgradeReason;

}
