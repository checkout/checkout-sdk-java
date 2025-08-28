package com.checkout.payments.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * provider
 * Details of the device ID provider.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Provider {

    /**
     * The unique identifier for the device.
     * [Optional]
     */
    private String id;

    /**
     * The name of the provider that generated the device identifier.
     * [Optional]
     */
    private String name;

}
