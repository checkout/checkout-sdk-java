package com.checkout.handlepaymentsandpayouts.setups.entities.customer;

import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common.OsType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer device information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CustomerDevice {

    /**
     * The locale setting of the customer's device (e.g., "en-US")
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
    @SerializedName("ipv4")
    private String ipv4;

    /**
     * The customer's device IPv6 address, used by some payment methods for risk and eligibility checks.
     * [Optional]
     */
    @SerializedName("ipv6")
    private String ipv6;

    /**
     * The type of client the customer uses to initiate the payment. Required for Cash App.
     * [Optional]
     */
    private CustomerDeviceClient client;

    /**
     * The operating system of the customer's device.
     * [Optional]
     */
    private OsType os;
}
