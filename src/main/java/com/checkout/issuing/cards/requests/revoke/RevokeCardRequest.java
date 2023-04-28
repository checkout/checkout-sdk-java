package com.checkout.issuing.cards.requests.revoke;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RevokeCardRequest {

    private RevokeReason reason;
}
