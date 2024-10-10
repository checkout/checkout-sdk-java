package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class AccountFundingTransaction {

    @SerializedName("aft_indicator")
    private AftIndicator aftIndicator;
}
