package com.checkout.marketplace;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CreateTransferRequest {

    private String reference;

    @SerializedName("transfer_type")
    private TransferType transferType;

    private TransferSource source;

    private TransferDestination destination;

}
