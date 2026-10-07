package com.checkout.handlepaymentsandpayouts.setups.entities.customer;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.OsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Details of the customer's device.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CustomerDevice {

    /**
     * The locale of the device, for example en_GB.
     * [Optional]
     */
    private String locale;

    /**
     * A unique identifier for the customer's device.
     * [Optional]
     */
    private String fingerprint;

    /**
     * The customer's device IPv4 address, used by some payment methods for risk and eligibility checks.
     * [Optional]
     */
    private String ipv4;

    /**
     * The customer's device IPv6 address, used by some payment methods for risk and eligibility checks.
     * [Optional]
     */
    private String ipv6;

    /**
     * The type of client the customer uses to initiate the payment. Required when using Cash App
     * Pay; a Cash App payment setup without it is rejected.
     * [Optional]
     * Enum: "web" "mobile_web" "app"
     */
    private CustomerDeviceClient client;

    /**
     * The operating system of the customer's device.
     * [Optional]
     * Enum: "android" "ios"
     */
    private OsType os;
}
