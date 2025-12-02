package com.checkout.handlepaymentsandpayouts.setups.entities.customer;

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
public class CustomerDevice {

    /**
     * The locale setting of the customer's device (e.g., "en-US")
     */
    private String locale;
}