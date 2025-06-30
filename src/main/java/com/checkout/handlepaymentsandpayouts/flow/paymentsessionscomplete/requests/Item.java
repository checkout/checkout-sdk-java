package com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Item {

    /**
     * The descriptive name of the line item.
     */
    private String name;

    /**
     * The number of line items.
     */
    private Long quantity;

    /**
     * The unit price of the item, in minor currency units.
     */
    @SerializedName("unit_price")
    private Long unitPrice;

    /**
     * The item reference or product stock keeping unit (SKU).
     */
    private String reference;

    /**
     * A code identifying a commodity for value-added tax (VAT) purposes.
     */
    @SerializedName("commodity_code")
    private String commodityCode;

    /**
     * The unit in which the item is measured, as a Unit of Measure (UoM) code.
     */
    @SerializedName("unit_of_measure")
    private String unitOfMeasure;

    /**
     * The total cost of the line item, in minor currency units. The value should include any tax and discounts applied
     * using the formula: value = (quantity x unit_price) - discount_amount.
     */
    @SerializedName("total_amount")
    private Long totalAmount;

    /**
     * The total amount of sales tax or value-added tax (VAT) on the total purchase amount. Tax should be included in the
     * total purchase amount.
     */
    @SerializedName("tax_amount")
    private Long taxAmount;

    /**
     * The discount applied to each invoice line item.
     */
    @SerializedName("discount_amount")
    private Long discountAmount;

    /**
     * Link to the line item's product page.
     */
    private String url;

    /**
     * Link to the line item's product image.
     */
    @SerializedName("image_url")
    private String imageUrl;

}
