package com.checkout.disputes.four;

import com.checkout.common.ThreeDSEnrollmentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ThreeDSVersionEnrollment {

    private String version;

    private ThreeDSEnrollmentStatus enrolled;

}