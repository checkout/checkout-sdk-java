package com.checkout.payments;

import com.checkout.common.ThreeDSEnrollmentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ThreeDSEnrollment {

    private Boolean downgraded;

    private ThreeDSEnrollmentStatus enrolled;

}
