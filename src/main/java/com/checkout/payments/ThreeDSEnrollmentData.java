package com.checkout.payments;

import com.checkout.common.ThreeDSEnrollmentStatus;
import lombok.Data;

@Data
public final class ThreeDSEnrollmentData {

    private boolean downgraded;

    private ThreeDSEnrollmentStatus enrolled;

}