package com.checkout.issuing.cards.requests.revoke;

import lombok.Builder;
import lombok.Data;

/**
 * The optional request body for POST /issuing/cards/{cardId}/revoke. The body itself is optional:
 * the client methods accept {@code null} and then send no body.
 */
@Data
@Builder
public final class RevokeCardRequest {

    /**
     * The reason why the card is being revoked.
     * <p>
     * [Optional]
     * </p>
     * Enum: "expired" "reported_lost" "reported_stolen"
     */
    private RevokeReason reason;
}
