package com.checkout.payments;

import com.checkout.common.CountryCode;
import com.checkout.payments.request.ItemSubType;
import com.checkout.payments.request.ItemType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class ProductRequest {

    /**
     * The item type.
     * [Optional]
     */
    private ItemType type;

    /**
     * The item sub-type.
     * [Optional]
     */
    private ItemSubType subType;

    /**
     * The item name.
     * [Optional]
     */
    private String name;

    /**
     * The number of items.
     * [Optional]
     */
    private Long quantity;

    /**
     * The price of the item.
     * [Optional]
     */
    private Long unitPrice;

    /**
     * A reference for the item.
     * [Optional]
     */
    private String reference;

    /**
     * The commodity code.
     * [Optional]
     */
    private String commodityCode;

    /**
     * The unit of measure.
     * [Optional]
     */
    private String unitOfMeasure;

    /**
     * The total amount of the item.
     * [Optional]
     */
    private Long totalAmount;

    /**
     * The tax rate applied to the item.
     * [Optional]
     */
    private Long taxRate;

    /**
     * The amount of tax applied to the item.
     * [Optional]
     */
    private Long taxAmount;

    /**
     * The amount discounted from the item.
     * [Optional]
     */
    private Long discountAmount;

    /**
     * The WeChat Pay goods ID.
     * [Optional]
     */
    private String wxpayGoodsId;

    /**
     * The URL of the product image.
     * [Optional]
     */
    private String imageUrl;

    /**
     * The URL of the product page.
     * [Optional]
     */
    private String url;

    /**
     * The stock keeping unit.
     * [Optional]
     */
    private String sku;

    /**
     * Maximum date for the service to be rendered or ended.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate serviceEndsOn;

    /**
     * The country in which the purchase was made.
     * [Optional]
     */
    private CountryCode purchaseCountry;

    /**
     * The amount in a foreign retailer's local currency.
     * [Optional]
     */
    private Long foreignRetailerAmount;

}
