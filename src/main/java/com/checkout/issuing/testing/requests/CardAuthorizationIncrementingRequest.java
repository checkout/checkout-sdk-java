package com.checkout.issuing.testing.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardAuthorizationIncrementingRequest {

    private int amount;
}
