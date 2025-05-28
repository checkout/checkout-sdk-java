package com.checkout.payments;

import com.checkout.payments.request.Device;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class RiskRequest {

    /**
     * Whether a risk assessment should be performed (Optional)
     */
    @Builder.Default
    private Boolean enabled = true;

    /**
     * Device session ID collected from our standalone Risk.js package. If you integrate using our Frames
     * solution, this ID is not required (Optional, pattern ^(dsid)_(\w{26})$)
     */
    @SerializedName("device_session_id")
    private String deviceSessionId;

    /**
     * Details of the device from which the payment originated (Optional)
     */
    private Device device;

}
