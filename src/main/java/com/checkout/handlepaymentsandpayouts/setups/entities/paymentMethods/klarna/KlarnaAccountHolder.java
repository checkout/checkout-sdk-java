package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna;

import com.checkout.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klarna account holder information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class KlarnaAccountHolder {

    /**
     * The billing address of the Klarna account holder.
     * [Optional]
     */
    private Address billingAddress;
}
