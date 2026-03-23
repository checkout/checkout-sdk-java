package com.checkout.issuing.testing.requests;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

/**
 * Details for the simulated OOB authentication transaction.
 */
@Data
@Builder
public class OobTransactionDetails {

    @SerializedName("last_four")
    private String lastFour;

    @SerializedName("merchant_name")
    private String merchantName;

    @SerializedName("purchase_amount")
    private Double purchaseAmount;

    @SerializedName("purchase_currency")
    private String purchaseCurrency;

}
