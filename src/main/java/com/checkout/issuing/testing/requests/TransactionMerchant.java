package com.checkout.issuing.testing.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionMerchant {

    private String categoryCode;
}
