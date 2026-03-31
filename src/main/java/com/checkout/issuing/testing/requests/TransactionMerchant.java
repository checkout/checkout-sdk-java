package com.checkout.issuing.testing.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class TransactionMerchant {

    private String categoryCode;
}
