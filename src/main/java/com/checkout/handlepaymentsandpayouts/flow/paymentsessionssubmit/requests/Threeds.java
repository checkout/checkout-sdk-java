package com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.requests;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.enums.ChallengeIndicatorType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.enums.ExemptionType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Threeds {

    /**
     * Default: false
     * Whether to process the payment as a 3D Secure payment. Payments where merchant_initiated is set to true will be
     * processed as 3DS Requestor Initiated.
     */
    @Builder.Default
    private Boolean enabled = false;

    /**
     * Default: false
     * Applies only when 3ds.enabled is set to true. Set to true to attempt the payment without 3DS when the issuer,
     * card, or network doesn't support 3DS.
     */
    @Builder.Default
    @SerializedName("attempt_n3d")
    private Boolean attemptN3d = false;

    /**
     * Default: "no_preference"
     * Specifies the preference for whether a 3DS challenge should be performed. Ultimately, whether the challenge is
     * presented to the customer or not is up to their bank's discretion.
     */
    @Builder.Default
    @SerializedName("challenge_indicator")
    private ChallengeIndicatorType challengeIndicator = ChallengeIndicatorType.NO_PREFERENCE;

    /**
     * Default: "no_preference"
     * Specifies an exemption reason for the payment to not be processed using 3D Secure authentication. For more
     * information on 3DS exemptions, refer to our SCA compliance guide.
     */
    @Builder.Default
    private ExemptionType exemption = ExemptionType.NO_PREFERENCE;

    /**
     * Default: true
     * Specifies whether to process the payment as 3D Secure, if authorization was soft declined due to 3DS
     * authentication being required. For processing channels created before October 12, 2022, the value will default to
     * false.
     */
    @Builder.Default
    @SerializedName("allow_upgrade")
    private Boolean allowUpgrade = true;

}
