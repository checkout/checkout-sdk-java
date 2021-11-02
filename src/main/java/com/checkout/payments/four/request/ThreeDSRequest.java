package com.checkout.payments.four.request;

import com.checkout.payments.Exemption;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ThreeDSRequest {

    private boolean enabled;

    @SerializedName("attempt_n3d")
    private boolean attemptN3D;

    private String eci;

    private String cryptogram;

    private String xid;

    private String version;

    private Exemption exemption;

}
