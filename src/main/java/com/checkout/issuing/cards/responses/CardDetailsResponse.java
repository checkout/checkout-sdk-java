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

    protected final CardType type;

    protected String id;

    protected String cardholderId;

    protected String cardProductId;

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

    protected String lastFour;

    protected Integer expiryMonth;

    protected Integer expiryYear;

    protected CardStatus status;

    protected String displayName;

    protected Currency billingCurrency;

    protected CountryCode issuingCountry;

    protected String reference;

    /**
     * User's metadata.
     * [Optional]
     */
    protected IssuingCardMetadata metadata;

    protected Instant createdDate;

    protected Instant lastModifiedDate;

    /**
     * ISO 8601 date scheduling the card's activation.
     * <p>
     * [Optional]
     * </p>
     */
    protected String activationDate;

    /**
     * Date for the card to be automatically revoked. Must be after the current date and date only in the
     * form yyyy-mm-dd.
     * <p>
     * [Optional]
     * </p>
     * Format: yyyy-MM-dd
     */
    protected LocalDate revocationDate;

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
