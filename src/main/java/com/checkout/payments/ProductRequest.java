package com.checkout.payments;

import com.checkout.common.CountryCode;
import com.checkout.payments.request.ItemSubType;
import com.checkout.payments.request.ItemType;
import com.google.gson.annotations.SerializedName;
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
    @SerializedName("sub_type")
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
    @SerializedName("unit_price")
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
    @SerializedName("commodity_code")
    private String commodityCode;

    /**
     * The unit of measure.
     * [Optional]
     */
    @SerializedName("unit_of_measure")
    private String unitOfMeasure;

    /**
     * The total amount of the item.
     * [Optional]
     */
    @SerializedName("total_amount")
    private Long totalAmount;

    /**
     * The tax rate applied to the item.
     * [Optional]
     */
    @SerializedName("tax_rate")
    private Long taxRate;

    /**
     * The amount of tax applied to the item.
     * [Optional]
     */
    @SerializedName("tax_amount")
    private Long taxAmount;

    /**
     * The amount discounted from the item.
     * [Optional]
     */
    @SerializedName("discount_amount")
    private Long discountAmount;

    /**
     * The WeChat Pay goods ID.
     * [Optional]
     */
    @SerializedName("wxpay_goods_id")
    private String wxpayGoodsId;

    /**
     * The URL of the product image.
     * [Optional]
     */
    @SerializedName("image_url")
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
    @SerializedName("service_ends_on")
    private LocalDate serviceEndsOn;

    /**
     * The country in which the purchase was made.
     * [Optional]
     */
    @SerializedName("purchase_country")
    private CountryCode purchaseCountry;

    /**
     * The amount in a foreign retailer's local currency.
     * [Optional]
     */
    @SerializedName("foreign_retailer_amount")
    private Long foreignRetailerAmount;

}
