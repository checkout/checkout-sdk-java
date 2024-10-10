package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class AftIndicator {

    @SerializedName("pull_funds")
    private PullFunds pullFunds;
}
