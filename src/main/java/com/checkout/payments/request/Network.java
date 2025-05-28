package com.checkout.payments.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Network {

    /**
     * The device's IPV4 address. Not required if you provide the ipv6 field (Optional)
     */
    private String ipv4;

    /**
     * The device's IPV6 address. Not required if you provide the ipv4 field (Optional)
     */
    private String ipv6;

    /**
     * Specifies if the Tor network was used in the browser session (Optional)
     */
    private Boolean tor;

    /**
     * Specifies if a virtual private network (VPN) was used in the browser session (Optional)
     */
    private Boolean vpn;

    /**
     * Specifies if a proxy was used in the browser session (Optional)
     */
    private Boolean proxy;

}
