package com.checkout.issuing.testing.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardAuthorizationClearingRequest {

    private int amount;
}
