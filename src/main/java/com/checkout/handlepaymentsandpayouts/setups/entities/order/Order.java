package com.checkout.handlepaymentsandpayouts.setups.entities.order;

import com.checkout.payments.ShippingDetails;
import com.checkout.payments.contexts.PaymentContextsItems;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

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
    @SerializedName("sub_merchants")
    private List<OrderSubMerchant> subMerchants;

    /**
     * >= 0
     * The discount amount applied to the order
     */
    @SerializedName("discount_amount")
    private Long discountAmount;
}