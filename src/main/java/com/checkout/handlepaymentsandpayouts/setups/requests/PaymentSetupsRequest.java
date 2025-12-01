package com.checkout.handlepaymentsandpayouts.setups.requests;

import com.checkout.HttpMetadata;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.Customer;
import com.checkout.handlepaymentsandpayouts.setups.entities.industry.Industry;
import com.checkout.handlepaymentsandpayouts.setups.entities.order.Order;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.PaymentMethods;
import com.checkout.handlepaymentsandpayouts.setups.entities.settings.Settings;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a payment setup request.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSetupsRequest {

    /**
     * A unique identifier that can be used to reference the payment setup.
     */
    private String reference;

    /**
     * The currency for the payment setup.
     */
    private Currency currency;

    /**
     * The customer details.
     */
    private Customer customer;

    /**
     * Information about the payment methods.
     */
    @SerializedName("payment_methods")
    private PaymentMethods paymentMethods;

    /**
     * Order information.
     */
    private Order order;

    /**
     * Industry-specific information.
     */
    private Industry industry;

    /**
     * The URLs used for redirection after the payment is processed.
     */
    private Settings settings;
}