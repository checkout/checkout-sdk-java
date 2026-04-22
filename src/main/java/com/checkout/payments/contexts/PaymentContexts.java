package com.checkout.payments.contexts;

import com.checkout.common.Currency;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ShippingDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentContexts {

    /**
     * The payment amount in minor currency units.
     * [Required]
     * min 0
     */
    private Long amount;

    /**
     * The three-letter ISO currency code.
     * [Required]
     * min 3 characters, max 3 characters
     */
    private Currency currency;

    /**
     * Must be "Recurring" if the payment is paid in multiple installments.
     * [Optional]
     * Enum: "Regular" "Recurring"
     */
    private PaymentType paymentType;

    /**
     * The authorization type for the payment.
     * [Optional]
     */
    private String authorizationType;

    /**
     * Whether to capture the later payment.
     * [Optional]
     */
    private Boolean capture;

    /**
     * The customer details. Required if source.type is StcPay or Tabby.
     * [Optional]
     */
    private PaymentContextsCustomerRequest customer;

    /**
     * The shipping details.
     * [Optional]
     */
    private ShippingDetails shipping;

    /**
     * Use the processing object to influence or override the data during payment processing.
     * [Optional]
     */
    private PaymentContextsProcessing processing;

    /**
     * The processing channel to be used for the payment.
     * [Optional]
     * Pattern: ^(pc)_(\w{26})$
     */
    private String processingChannelId;

    /**
     * A reference you can later use to identify this Payment Context, such as an order number.
     * [Optional]
     * max 50 characters
     */
    private String reference;

    /**
     * A description of the Payment Context.
     * [Optional]
     * max 100 characters
     */
    private String description;

    /**
     * For redirect payment methods, this overrides the default success redirect URL configured on your account.
     * [Optional]
     * Format: uri
     * max 1024 characters
     */
    private String successUrl;

    /**
     * For redirect payment methods, this overrides the default failure redirect URL configured on your account.
     * [Optional]
     * Format: uri
     * max 1024 characters
     */
    private String failureUrl;

    /**
     * The order's line items. Required for Klarna payments.
     * [Optional]
     */
    private List<PaymentContextsItems> items;

    /**
     * A set of key-value pairs to store additional information about the transaction.
     * Up to 20 fields, max 255 characters per value.
     * [Optional]
     */
    private Map<String, Object> metadata;
}
