package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class RiskRequest {

    private boolean enabled;

    @SerializedName("device_session_id")
    private String deviceSessionId;

}

