package com.checkout.handlepaymentsandpayouts.setups.entities.billing;

import com.checkout.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The billing details for the payment setup.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentSetupBilling {

    /**
     * The billing address.
     */
    private Address address;
}
