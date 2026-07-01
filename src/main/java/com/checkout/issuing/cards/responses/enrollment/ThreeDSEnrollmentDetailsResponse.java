package com.checkout.issuing.cards.responses.enrollment;

import com.checkout.common.Phone;
import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public final class ThreeDSEnrollmentDetailsResponse extends Resource {

    private String locale;

    private Phone phoneNumber;

    private Instant createdDate;

    private Instant lastModifiedDate;
}
