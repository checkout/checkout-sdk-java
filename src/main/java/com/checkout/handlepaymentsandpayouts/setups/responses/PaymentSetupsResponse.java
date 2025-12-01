package com.checkout.handlepaymentsandpayouts.setups.responses;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a payment setup response.
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
     * The processing channel ID.
     */
    @SerializedName("processing_channel_id")
    private String processingChannelId;

    /**
     * The payment amount in the minor currency unit.
     */
    private Long amount;

    /**
     * The currency for the payment setup.
     */
    private Currency currency;

    /**
     * The type of payment.
     */
    @SerializedName("payment_type")
    private String paymentType;

    /**
     * A unique identifier that can be used to reference the payment setup.
     */
    private String reference;

    /**
     * A description of the payment setup.
     */
    private String description;

    /**
     * Information about the payment methods.
     */
    @SerializedName("payment_methods")
    private PaymentMethods paymentMethods;

    /**
     * The URLs used for redirection after the payment is processed.
     */
    private Settings settings;

    /**
     * The customer details.
     */
    private Customer customer;

    /**
     * Order information.
     */
    private Order order;

    /**
     * Industry-specific information.
     */
    private Industry industry;
}