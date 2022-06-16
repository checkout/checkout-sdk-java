package com.checkout.marketplace.transfers;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class CreateTransferRequest {

    private String reference;

    @SerializedName("transfer_type")
    private TransferType transferType;

    private TransferSourceRequest source;

    private TransferDestinationRequest destination;

}
