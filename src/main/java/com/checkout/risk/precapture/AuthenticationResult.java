package com.checkout.risk.precapture;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class AuthenticationResult {

    private Boolean attempted;

    private Boolean challenged;

    private Boolean succeeded;

    private Boolean liabilityShifted;

    private String method;

    private String version;

}
