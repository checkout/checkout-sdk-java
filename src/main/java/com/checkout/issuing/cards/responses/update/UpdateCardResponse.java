package com.checkout.issuing.cards.responses.update;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.checkout.issuing.cards.CardStatus;
import com.checkout.issuing.cards.CardType;
import com.checkout.issuing.cards.IssuingScheme;
import com.checkout.issuing.cards.requests.update.IssuingCardMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Response returned when updating a card.
 * <p>
 * As of the 2026-09-17 API change, {@code update-card-response} is defined as the full
 * {@code get-card-response} field set (see {@link com.checkout.issuing.cards.responses.CardDetailsResponse})
 * with {@code lastModifiedDate} required. It no longer includes {@code encrypted_cvv}: that field
 * was added by the 2026-09-02 change and removed again by this one.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class UpdateCardResponse extends Resource {

    private String id;

    private String cardholderId;

    private String cardProductId;

    private String clientId;

    /**
     * The entity's unique identifier.
     * [Optional]
     * ^ent_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    private String entityId;

    /**
     * The Dashboard user's unique identifier.
     * [Optional]
     * ^usr_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    private String userId;

    private String lastFour;

    private Integer expiryMonth;

    private Integer expiryYear;

    private CardStatus status;

    private CardType type;

    private String displayName;

    private Currency billingCurrency;

    private CountryCode issuingCountry;

    private String reference;

    /**
     * User's metadata.
     * [Optional]
     */
    private IssuingCardMetadata metadata;

    private Instant createdDate;

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
     * Date for the card to be automatically revoked. Must be after the current date and date only in the
     * form yyyy-mm-dd.
     * <p>
     * [Optional]
     * </p>
     * Format: yyyy-MM-dd
     *
     * @deprecated Use {@link #scheduledRevocationDate} instead.
     */
    @Deprecated
    private LocalDate revocationDate;

    /**
     * The card will be revoked at midnight UTC on the date specified.
     * <p>
     * [Optional]
     * </p>
     * Format: yyyy-MM-dd
     */
    private LocalDate scheduledRevocationDate;

    /**
     * The date and time the card was last activated.
     * <p>
     * [Optional, nullable, read only]
     * </p>
     * Returns {@code null} if the card has never been activated.
     */
    private Instant lastActivatedOn;

    /**
     * When a card is renewed, the unique identifier of the first card in the renewal history.
     * [Optional]
     * ^crd_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    private String rootCardId;

    /**
     * When a card is renewed, the unique identifier of the previous card in the renewal history.
     * [Optional]
     * ^crd_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    private String parentCardId;

    /**
     * The card scheme.
     * [Optional]
     * Enum: "mastercard" "visa"
     */
    private IssuingScheme scheme;

    /**
     * The date and time when the card was last modified, in UTC.
     * [Required]
     * Format: date-time (RFC 3339)
     */
    private Instant lastModifiedDate;

    /**
     * Specifies whether the virtual card is set to expire after a single use.
     * Only present when the underlying card is virtual; physical cards never send it.
     * [Optional]
     */
    private Boolean isSingleUse;
}
