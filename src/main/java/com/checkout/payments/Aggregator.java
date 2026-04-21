package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Information about the payment aggregator.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Aggregator {

    /**
     * The sub-merchant ID.
     */
    @SerializedName("sub_merchant_id")
    private String subMerchantId;

    /**
     * The Visa identifier for the payment aggregator.
     */
    @SerializedName("aggregator_id_visa")
    private String aggregatorIdVisa;

    /**
     * The Mastercard identifier for the payment aggregator.
     */
    @SerializedName("aggregator_id_mc")
    private String aggregatorIdMc;
}
