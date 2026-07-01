package com.checkout.payments.contexts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentContextsItems {

    private PaymentContextsItemsType type;

    private String name;

    private Integer quantity;

    private Integer unitPrice;

    private String reference;

    private Integer totalAmount;

    private Integer taxRate;

    private Integer taxAmount;

    private Integer discountAmount;

    private String wxpayGoodsId;

    private String url;

    private String imageUrl;

    private String serviceEndsOn;

}
