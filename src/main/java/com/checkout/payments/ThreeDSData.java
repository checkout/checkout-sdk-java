package com.checkout.payments;

import com.checkout.common.Exemption;
import com.checkout.common.ThreeDSEnrollmentStatus;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class ThreeDSData {

    private Boolean downgraded;

    private ThreeDSEnrollmentStatus enrolled;

    @SerializedName("upgrade_reason")
    private String upgradeReason;

    @SerializedName("signature_valid")
    private String signatureValid;

    @SerializedName("authentication_response")
    private String authenticationResponse;

    private String cryptogram;

    private String xid;

    private String version;

    private Exemption exemption;

    @SerializedName("exemption_applied")
    private String exemptionApplied;

    private Boolean challenged;

}
