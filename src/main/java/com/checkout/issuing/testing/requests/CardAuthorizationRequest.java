package com.checkout.issuing.testing.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardAuthorizationRequest {

    private CardSimulation card;

    private TransactionSimulation transaction;
}
