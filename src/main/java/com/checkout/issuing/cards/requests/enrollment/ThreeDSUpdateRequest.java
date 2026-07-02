package com.checkout.issuing.cards.requests.enrollment;

import com.checkout.common.Phone;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ThreeDSUpdateRequest {

    private SecurityPair securityPair;

    private String password;

    private String locale;

    private Phone phoneNumber;
}
