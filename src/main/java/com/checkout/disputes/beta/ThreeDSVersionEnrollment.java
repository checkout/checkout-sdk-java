package com.checkout.disputes.beta;

import com.checkout.common.beta.ThreeDSEnrollmentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ThreeDSVersionEnrollment {

    private String version;

    private ThreeDSEnrollmentStatus enrolled;

}