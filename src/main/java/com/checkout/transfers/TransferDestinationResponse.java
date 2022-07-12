package com.checkout.transfers;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public final class TransferDestinationResponse {

    @SerializedName("entity_id")
    private String entityId;

}
