package com.checkout.issuing.cards.responses.activate;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

/**
 * Response returned when activating a card.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ActivateCardResponse extends Resource {

    /**
     * The time the card was activated.
     * <p>
     * [Required]
     * </p>
     */
    private Instant lastActivatedOn;
}
