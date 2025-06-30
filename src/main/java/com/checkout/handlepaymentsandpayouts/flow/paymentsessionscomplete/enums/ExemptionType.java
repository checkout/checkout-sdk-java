package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.enums;

import com.google.gson.annotations.SerializedName;

/** Default: "no_preference" Specifies an exemption reason for the payment to not be processed using 3D Secure
 * authentication. For more information on 3DS exemptions, refer to our SCA compliance guide.
 */
public enum ExemptionType {
    @SerializedName("low_value")
    LOW_VALUE,
    @SerializedName("trusted_listing")
    TRUSTED_LISTING,
    @SerializedName("trusted_listing_prompt")
    TRUSTED_LISTING_PROMPT,
    @SerializedName("transaction_risk_assessment")
    TRANSACTION_RISK_ASSESSMENT,
    @SerializedName("3ds_outage")
    THREEDS_OUTAGE,
    @SerializedName("sca_delegation")
    SCA_DELEGATION,
    @SerializedName("out_of_sca_scope")
    OUT_OF_SCA_SCOPE,
    @SerializedName("low_risk_program")
    LOW_RISK_PROGRAM,
    @SerializedName("recurring_operation")
    RECURRING_OPERATION,
    @SerializedName("data_share")
    DATA_SHARE,
    @SerializedName("other")
    OTHER,
    @SerializedName("no_preference")
    NO_PREFERENCE
}
