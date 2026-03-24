package com.checkout.issuing.testing.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CardAuthorizationIncrementingRequest {

    private int amount;
}
