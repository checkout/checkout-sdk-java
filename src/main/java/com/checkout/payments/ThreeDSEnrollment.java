package com.checkout.payments;

import com.checkout.common.ThreeDSEnrollmentStatus;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ThreeDSEnrollment {

    private Boolean downgraded;

    private ThreeDSEnrollmentStatus enrolled;

    @SerializedName("upgrade_reason")
    private String upgradeReason;

}
