package com.checkout.handlepaymentsandpayouts.setups.entities.order;

import com.checkout.payments.ShippingDetails;
import com.checkout.payments.contexts.PaymentContextsItems;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The customer's order details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Order {

    /**
     * A list of items in the order.
     * [Optional]
     */
    private List<PaymentContextsItems> items;

    /**
     * The customer's shipping details.
     * [Optional]
     */
    private ShippingDetails shipping;

    /**
     * The details of the sub-merchants.
     * [Optional]
     */
    private List<OrderSubMerchant> subMerchants;

    /**
     * The discount amount the merchant applied to the transaction.
     * [Optional]
     * min 0
     */
    private Long discountAmount;

    /**
     * The unique identifier for the invoice.
     * [Optional]
     */
    private String invoiceId;

    /**
     * The total shipping amount for the order.
     * [Optional]
     * min 0
     */
    private Long shippingAmount;

    /**
     * The total surcharge amount for the order.
     * [Optional]
     * Format: int64
     * min 0
     */
    private Long surchargeAmount;

    /**
     * The total tax amount for the order.
     * [Optional]
     * min 0
     */
    private Long taxAmount;

    /**
     * The total tipping amount for the order.
     * [Optional]
     * Format: int64
     * min 0
     */
    private Long tippingAmount;

    /**
     * The sub-entities that the payment is being processed on behalf of.
     * [Optional]
     * min 1 item, max 50 items
     */
    private List<PaymentSetupAmountAllocation> amountAllocations;
}
