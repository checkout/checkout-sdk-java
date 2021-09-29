package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class Ds {

    @SerializedName("ds_id")
    private String dsId;

    @SerializedName("reference_number")
    private String referenceNumber;

    @SerializedName("transaction_id")
    private String transactionId;

}
