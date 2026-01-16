package com.checkout.handlepaymentsandpayouts.setups.responses;

import com.checkout.HttpMetadata;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.setups.entities.customer.Customer;
import com.checkout.handlepaymentsandpayouts.setups.entities.industry.Industry;
import com.checkout.handlepaymentsandpayouts.setups.entities.order.Order;
import com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.PaymentMethods;
import com.checkout.handlepaymentsandpayouts.setups.entities.settings.Settings;
import com.checkout.payments.PaymentType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Payment setup response
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSetupsResponse extends HttpMetadata {

    /**
     * The unique identifier of the payment setup.
     */
    private String id;

    /**
     * The processing channel used for the payment setup
     */
    @SerializedName("processing_channel_id")
    private String processingChannelId;

    /**
     * The payment amount. The exact format depends on the currency
     */
    private Long amount;

    /**
     * The three-letter ISO currency code
     */
    private Currency currency;

    /**
     * The type of payment method
     */
    @SerializedName("payment_type")
    private PaymentType paymentType;

    /**
     * The reference identifier for this payment setup
     */
    private String reference;

    /**
     * A description of the payment setup
     */
    private String description;

    /**
     * The payment method configuration for this setup
     */
    @SerializedName("payment_methods")
    private PaymentMethods paymentMethods;

    /**
     * The payment setup configuration settings
     */
    private Settings settings;

    /**
     * Details about the customer
     */
    private Customer customer;

    /**
     * Details about the order associated with this payment setup
     */
    private Order order;

    /**
     * Industry-specific information for specialized payment scenarios
     */
    private Industry industry;
}