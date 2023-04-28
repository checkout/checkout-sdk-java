package com.checkout.issuing.cards.requests.suspend;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuspendCardRequest {

    private SuspendReason reason;
}
