package com.checkout.handlepaymentsandpayouts.setups.requests;

import com.checkout.HttpMetadata;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentType;
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
 * Payment setup request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSetupsRequest {

    /**
     * The processing channel to be used for the payment setup
     * [Required]
     */
    @SerializedName("processing_channel_id")
    private String processingChannelId;

    /**
     * The payment amount, in the minor currency unit. The exact format depends on the currency
     * [Required]
     */
    private Long amount;

    /**
     * The currency of the payment, as a three-letter ISO currency code
     * [Required]
     */
    private Currency currency;

    /**
     * The type of payment.
     //// You must provide this field for card payments in which the cardholder is not present. For example, if the transaction is a recurring payment, or a mail order/telephone order (MOTO) payment.
     * Enum: "Regular" "Recurring" "MOTO" "Installment" "Unscheduled"
     * [Optional]
     */
    @SerializedName("payment_type")
    private PaymentType paymentType = PaymentType.REGULAR;

    /**
     * A reference you can use to identify the payment. For example, an order number
     * [Optional]
     */
    private String reference;

    /**
     * A description of the payment setup
     * [Optional]
     */
    private String description;

    /**
     * The payment methods that are enabled on your account and available for use
     * [Optional]
     */
    @SerializedName("payment_methods")
    private PaymentMethods paymentMethods;

    /**
     * The payment setup configuration settings
     * [Optional]
     */
    private Settings settings;

    /**
     * Details about the customer
     * [Optional]
     */
    private Customer customer;

    /**
     * Details about the order associated with this payment setup
     * [Optional]
     */
    private Order order;

    /**
     * Details for specific industries, including airline and accommodation industries
     * [Optional]
     */
    private Industry industry;
}