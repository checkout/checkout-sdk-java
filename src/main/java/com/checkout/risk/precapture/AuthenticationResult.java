package com.checkout.risk.precapture;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class AuthenticationResult {

    private Boolean attempted;

    private Boolean challenged;

    private Boolean succeeded;

    @SerializedName("liability_shifted")
    private Boolean liabilityShifted;

    private String method;

    private String version;

}
