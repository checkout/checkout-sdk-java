package com.checkout.issuing.cards.responses.update;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

/**
 * The response returned when a card's details are updated.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateCardResponse extends Resource {

    /**
     * The date and time when the card was last modified, in UTC.
     * [Required]
     * Format: date-time (RFC 3339)
     */
    private Instant lastModifiedDate;

    /**
     * The card's encrypted CVV, returned when the return-encrypted-cvv header is true.
     * [Optional]
     */
    private String encryptedCvv;
}
