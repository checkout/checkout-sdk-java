package com.checkout.handlepaymentsandpayouts.setups.entities.order;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Sub-merchant information for order
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSubMerchant {

    /**
     * The unique identifier of the sub-merchant
     */
    private String id;

    /**
     * The category of products or services offered by the sub-merchant
     */
    @SerializedName("product_category")
    private String productCategory;

    /**
     * The number of trades or transactions the sub-merchant has conducted
     */
    @SerializedName("number_of_trades")
    private Integer numberOfTrades;

    /**
     * The date when the sub-merchant was registered in YYYY-MM-DD format
     */
    @SerializedName("registration_date")
    private Instant registrationDate;
}