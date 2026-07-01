package com.checkout.handlepaymentsandpayouts.setups.entities.order;

import com.checkout.payments.ShippingDetails;
import com.checkout.payments.contexts.PaymentContextsItems;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Order information for payment setup
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Order {

    /**
     * The items included in the order
     */
    private List<PaymentContextsItems> items;

    /**
     * The shipping information for the order
     */
    private ShippingDetails shipping;

    /**
     * Information about sub-merchants involved in the order
     */
    private List<OrderSubMerchant> subMerchants;

    /**
     * &gt;= 0
     * The discount amount applied to the order
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
     * &gt;= 0
     */
    private Long shippingAmount;

    /**
     * The total surcharge amount for the order.
     * [Optional]
     * &gt;= 0
     */
    private Long surchargeAmount;

    /**
     * The total tax amount for the order.
     * [Optional]
     * &gt;= 0
     */
    private Long taxAmount;

    /**
     * The total tipping amount for the order.
     * [Optional]
     * &gt;= 0
     */
    private Long tippingAmount;

    /**
     * The amount allocations representing the sub-entities on whose behalf the payment is processed
     */
    private List<PaymentSetupAmountAllocation> amountAllocations;
}