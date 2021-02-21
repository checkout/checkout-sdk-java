package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("attempt_n3d")
    private Boolean attemptN3D;
    private String eci;
    private String cryptogram;
    private String xid;
    private String version;

    public static ThreeDSRequest from(boolean enabled) {
        ThreeDSRequest request = new ThreeDSRequest();
        request.enabled = enabled;
        return request;
    }
}
