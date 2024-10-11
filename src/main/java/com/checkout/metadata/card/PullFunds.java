package com.checkout.metadata.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class PullFunds {

    @SerializedName("cross_border")
    private Boolean crossBorder;

    private Boolean domestic;
}
