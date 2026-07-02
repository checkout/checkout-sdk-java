package com.checkout.transfers;

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

    private TransferType transferType;

    private TransferSourceRequest source;

    private TransferDestinationRequest destination;

}
