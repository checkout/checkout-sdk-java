package com.checkout.issuing.cards.requests.create;

import com.checkout.issuing.cards.requests.update.IssuingCardMetadata;
import com.checkout.issuing.cards.CardType;
import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class CardRequest {

    /**
     * The card type.
     * [Required]
     */
    private final CardType type;

    /**
     * The cardholder's unique identifier.
     * [Required]
     * Pattern: ^crh_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    private String cardholderId;

    /**
     * The duration of time during which the card will accept incoming transactions.
     * [Optional]
     */
    private CardLifetime lifetime;

    /**
     * Your reference.
     * [Optional]
     * max 256 characters
     */
    private String reference;

    /**
     * User's metadata.
     * <p>
     * [Optional]
     * </p>
     */
    private IssuingCardMetadata metadata;

    /**
     * Date scheduling the card's automatic revocation.
     * <p>
     * [Optional]
     * </p>
     * Format: date (YYYY-MM-DD, time is midnight UTC)
     * Example: 2027-03-12
     */
    private LocalDate revocationDate;

    /**
     * Date scheduling the card's first activation. Only applies to the initial activation of a
     * card. Two formats are supported: date only (YYYY-MM-DD, treated as midnight UTC), or date
     * with round hour (YYYY-MM-DDTHH:mmZ in UTC, or YYYY-MM-DDTHH:mm+HH:mm with offset). Only
     * round hours are allowed when a time is provided (HH:00). The value must be at least the next
     * round hour after the request time.
     * <p>
     * [Optional]
     * </p>
     * Example: 2026-06-01T10:00Z
     */
    private String scheduledActivationDate;

    /**
     * The card product's unique identifier. Required if the entity has more than one card
     * product.
     * [Required]
     */
    private String cardProductId;

    /**
     * The name to display on the card.
     * [Optional]
     * Pattern: ^[0-9a-zA-Z.\- ]{2,26}$
     * min 2 characters, max 26 characters
     */
    private String displayName;

    /**
     * Sets whether to activate the newly created card upon creation.
     * [Optional]
     */
    private Boolean activateCard;

    protected CardRequest(final CardType type,
                          final String cardholderId,
                          final CardLifetime lifetime,
                          final String reference,
                          final String cardProductId,
                          final String displayName,
                          final Boolean activateCard) {
        this.type = type;
        this.cardholderId = cardholderId;
        this.lifetime = lifetime;
        this.reference = reference;
        this.cardProductId = cardProductId;
        this.displayName = displayName;
        this.activateCard = activateCard;
    }
}
