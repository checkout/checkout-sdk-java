package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class MerchantRiskInfo {

    @SerializedName("delivery_email")
    private String deliveryEmail;

    @SerializedName("delivery_timeframe")
    private DeliveryTimeframe deliveryTimeframe;

    @SerializedName("is_preorder")
    private Boolean isPreorder;

    @SerializedName("is_reorder")
    private Boolean isReorder;

    @SerializedName("shipping_indicator")
    private ShippingIndicator shippingIndicator;

    @SerializedName("reorder_items_indicator")
    private ReorderItemsIndicatorType reorderItemsIndicator;

    @SerializedName("pre_order_purchase_indicator")
    private PreOrderPurchaseIndicator preOrderPurchaseIndicator;
    
    @SerializedName("pre_order_date")
    private Instant preOrderDate;
    
    @SerializedName("gift_card_amount")
    private String giftCardAmount;
    
    @SerializedName("gift_card_currency")
    private String giftCardCurrency;

    @SerializedName("gift_card_count")
    private String giftCardCount;

}
