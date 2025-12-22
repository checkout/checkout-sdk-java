package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.klarna;

import com.checkout.common.Address;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klarna account holder information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KlarnaAccountHolder {

    /**
     * The billing address of the Klarna account holder
     */
    @SerializedName("billing_address")
    private Address billingAddress;
}