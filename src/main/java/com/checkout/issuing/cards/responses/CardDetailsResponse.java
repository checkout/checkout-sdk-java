package com.checkout.issuing.cards.responses;

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

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class CardDetailsResponse extends Resource {

    /**
     * The card type.
     * [Required]
     */
    protected final CardType type;

    /**
     * The card's unique identifier.
     * [Required]
     * Pattern: ^crd_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    protected String id;

    /**
     * The cardholder's unique identifier.
     * [Required]
     * Pattern: ^crh_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    protected String cardholderId;

    /**
     * The card product's unique identifier.
     * [Required]
     * Pattern: ^pro_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    protected String cardProductId;

    /**
     * The client's unique identifier.
     * [Required]
     * Pattern: ^cli_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    protected String clientId;

    /**
     * The entity's unique identifier.
     * [Optional]
     * ^ent_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    protected String entityId;

    /**
     * The Dashboard user's unique identifier.
     * [Optional]
     * ^usr_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    protected String userId;

    /**
     * The last four digits of the card number, also known as the PAN.
     * [Required]
     * Pattern: ^[0-9]{4}$
     */
    protected String lastFour;

    /**
     * The card's expiration month.
     * [Required]
     * min 1, max 12
     */
    protected Integer expiryMonth;

    /**
     * The card's expiration year.
     * [Required]
     * min 4 characters, max 4 characters
     */
    protected Integer expiryYear;

    /**
     * The card's status, which determines whether it can approve incoming transactions.
     * [Required]
     */
    protected CardStatus status;

    /**
     * The name to display on the card.
     * [Optional]
     * Pattern: ^[0-9a-zA-Z.\- ]{2,26}$
     * min 2 characters, max 26 characters
     */
    protected String displayName;

    /**
     * The issuing currency, as a three-letter ISO 4217 currency code.
     * [Required]
     */
    protected Currency billingCurrency;

    /**
     * The issuing country, as a two-letter ISO 3166-1 alpha-2 country code.
     * [Required]
     * min 2 characters, max 2 characters
     */
    protected CountryCode issuingCountry;

    /**
     * Your reference.
     * [Optional]
     * max 256 characters
     */
    protected String reference;

    /**
     * User's metadata.
     * [Optional]
     */
    protected IssuingCardMetadata metadata;

    /**
     * The date and time when the card was created, in UTC.
     * [Optional]
     */
    protected Instant createdDate;

    /**
     * The date and time when the card was last modified, in UTC.
     * [Optional]
     */
    protected Instant lastModifiedDate;

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
    protected String scheduledActivationDate;

    /**
     * Date scheduling the card's automatic revocation.
     * <p>
     * [Optional]
     * </p>
     * Format: yyyy-MM-dd
     *
     * @deprecated Use {@link #scheduledRevocationDate} instead.
     */
    @Deprecated
    protected LocalDate revocationDate;

    /**
     * The card will be revoked at midnight UTC on the date specified.
     * <p>
     * [Optional]
     * </p>
     * Format: yyyy-MM-dd
     */
    protected LocalDate scheduledRevocationDate;

    /**
     * The date and time the card was last activated.
     * <p>
     * [Optional, nullable, read only]
     * </p>
     * Returns {@code null} if the card has never been activated.
     */
    protected Instant lastActivatedOn;

    /**
     * When a card is renewed, the unique identifier of the first card in the renewal history.
     * [Optional]
     * ^crd_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    protected String rootCardId;

    /**
     * When a card is renewed, the unique identifier of the previous card in the renewal history.
     * [Optional]
     * ^crd_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    protected String parentCardId;

    /**
     * The card scheme.
     * [Optional]
     * Enum: "mastercard" "visa"
     */
    protected IssuingScheme scheme;

    protected CardDetailsResponse(final CardType type) {
        this.type = type;
    }
}
