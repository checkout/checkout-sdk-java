package com.checkout.sessions;

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

    private String deliveryEmail;

    private DeliveryTimeframe deliveryTimeframe;

    private Boolean isPreorder;

    private Boolean isReorder;

    private ShippingIndicator shippingIndicator;

    private ReorderItemsIndicatorType reorderItemsIndicator;

    private PreOrderPurchaseIndicator preOrderPurchaseIndicator;
    
    private Instant preOrderDate;
    
    private String giftCardAmount;
    
    private String giftCardCurrency;

    private String giftCardCount;

}
