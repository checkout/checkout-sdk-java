package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Payment method option configuration
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodOption {

    /**
     * The unique identifier for the payment method option
     */
    private String id;

    /**
     * The status of the payment method option
     */
    private String status;

    /**
     * Configuration flags for the payment method option
     */
    private List<String> flags;

    /**
     * The action configuration for the payment method option
     */
    private PaymentMethodAction action;
}