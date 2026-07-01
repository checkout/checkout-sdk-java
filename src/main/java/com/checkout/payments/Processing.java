package com.checkout.payments;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Processing {

    private String retrievalReferenceNumber;

    private String acquirerReferenceNumber;

    private String acquirerTransactionId;

}