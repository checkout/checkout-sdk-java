package com.checkout.risk.precapture;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class AuthorizationResult {

    private String avsCode;

    private String cvvResult;

}
