package com.checkout.sessions;

import com.checkout.common.Exemption;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class ThreeDSExemption {

    private String requested;

    private Exemption applied;

    private String code;

    @SerializedName("trusted_beneficiary")
    private TrustedBeneficiary trustedBeneficiary;
}
