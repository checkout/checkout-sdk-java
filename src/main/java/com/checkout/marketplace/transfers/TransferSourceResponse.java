package com.checkout.marketplace.transfers;

import com.checkout.common.Currency;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class TransferSourceResponse {

    @SerializedName("entity_id")
    private String entityId;

    private Long amount;

    private Currency currency;
}
