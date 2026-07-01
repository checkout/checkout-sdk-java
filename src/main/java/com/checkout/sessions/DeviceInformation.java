package com.checkout.sessions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Details of the device from which the authentication originated.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DeviceInformation {

    /**
     * The unique identifier for the device.
     * [Optional]
     */
    private String deviceId;

    /**
     * Device session ID collected from the standalone Risk.js package.
     * [Optional]
     * Pattern: ^(dsid)_(\w{26})$
     */
    private String deviceSessionId;
}
