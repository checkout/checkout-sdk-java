package com.checkout.issuing.cards.requests.enrollment;

import com.checkout.common.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class ThreeDSEnrollmentRequest {

    private String locale;

    private Phone phoneNumber;
}
