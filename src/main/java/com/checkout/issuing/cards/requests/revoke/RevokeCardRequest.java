package com.checkout.issuing.cards.requests.revoke;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class RevokeCardRequest {

    private RevokeReason reason;
}
