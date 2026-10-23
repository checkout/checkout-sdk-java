package com.checkout.issuing.cards.requests.suspend;

import lombok.Builder;
import lombok.Data;

/**
 * The optional request body for POST /issuing/cards/{cardId}/suspend. The body itself is optional:
 * the client methods accept {@code null} and then send no body.
 */
@Data
@Builder
public final class SuspendCardRequest {

    /**
     * The reason why the card is being suspended.
     * <p>
     * [Optional]
     * </p>
     * Enum: "suspected_lost" "suspected_stolen"
     */
    private SuspendReason reason;
}
