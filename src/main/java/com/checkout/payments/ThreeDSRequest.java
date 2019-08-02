package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreeDSRequest {
    private boolean enabled;
    private Boolean attemptN3D;
    private String eci;
    private String cryptogram;
    private String xid;

    public static ThreeDSRequest from(boolean enabled) {
        ThreeDSRequest request = new ThreeDSRequest();
        request.enabled = enabled;
        return request;
    }
}