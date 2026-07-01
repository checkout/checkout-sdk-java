package com.checkout.issuing.cards.responses;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Resource;
import com.checkout.issuing.cards.CardStatus;
import com.checkout.issuing.cards.CardType;
import com.checkout.issuing.cards.IssuingScheme;
import com.checkout.issuing.cards.requests.update.IssuingCardMetadata;
import com.google.gson.annotations.SerializedName;
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

    @SerializedName("cardholder_id")
    protected String cardholderId;

    @SerializedName("card_product_id")
    protected String cardProductId;

    @SerializedName("client_id")
    protected String clientId;

    /**
     * The entity's unique identifier.
     * [Optional]
     * ^ent_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    @SerializedName("entity_id")
    protected String entityId;

    /**
     * The Dashboard user's unique identifier.
     * [Optional]
     * ^usr_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    @SerializedName("user_id")
    protected String userId;

    @SerializedName("last_four")
    protected String lastFour;

    @SerializedName("expiry_month")
    protected Integer expiryMonth;

    @SerializedName("expiry_year")
    protected Integer expiryYear;

    protected CardStatus status;

    @SerializedName("display_name")
    protected String displayName;

    @SerializedName("billing_currency")
    protected Currency billingCurrency;

    @SerializedName("issuing_country")
    protected CountryCode issuingCountry;

    protected String reference;

    /**
     * User's metadata.
     * [Optional]
     */
    protected IssuingCardMetadata metadata;

    @SerializedName("created_date")
    protected Instant createdDate;

    @SerializedName("last_modified_date")
    protected Instant lastModifiedDate;

    /**
     * ISO 8601 date scheduling the card's activation.
     * <p>
     * [Optional]
     * </p>
     */
    @SerializedName("activation_date")
    protected String activationDate;

    /**
     * Date for the card to be automatically revoked. Must be after the current date and date only in the
     * form yyyy-mm-dd.
     * <p>
     * [Optional]
     * </p>
     * Format: yyyy-MM-dd
     */
    @SerializedName("revocation_date")
    protected LocalDate revocationDate;

    /**
     * When a card is renewed, the unique identifier of the first card in the renewal history.
     * [Optional]
     * ^crd_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    @SerializedName("root_card_id")
    protected String rootCardId;

    /**
     * When a card is renewed, the unique identifier of the previous card in the renewal history.
     * [Optional]
     * ^crd_[a-z0-9]{26}$
     * min 30 characters, max 30 characters
     */
    @SerializedName("parent_card_id")
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
