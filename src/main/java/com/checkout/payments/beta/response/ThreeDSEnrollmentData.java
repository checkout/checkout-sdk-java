package com.checkout.payments.beta.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ThreeDSEnrollmentData {

    private boolean downgraded;

    private ThreeDSEnrollmentStatus enrolled;

}