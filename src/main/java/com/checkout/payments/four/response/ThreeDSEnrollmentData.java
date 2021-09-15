package com.checkout.payments.four.response;

import com.checkout.common.four.ThreeDSEnrollmentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ThreeDSEnrollmentData {

    private boolean downgraded;

    private ThreeDSEnrollmentStatus enrolled;

}