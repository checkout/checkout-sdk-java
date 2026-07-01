package com.checkout.issuing.cards.requests.create;

import com.checkout.issuing.cards.requests.update.IssuingCardMetadata;
import com.checkout.issuing.cards.CardType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class CardRequest {

    private final CardType type;

    @SerializedName("cardholder_id")
    private String cardholderId;

    private CardLifetime lifetime;

    private String reference;

    /**
     * User's metadata.
     * <p>
     * [Optional]
     * </p>
     */
    private IssuingCardMetadata metadata;

    /**
     * Date for the card to be automatically revoked. Must be after the current date.
     * <p>
     * [Optional]
     * </p>
     * Format: yyyy-MM-dd
     */
    @SerializedName("revocation_date")
    private LocalDate revocationDate;

    /**
     * ISO 8601 date scheduling the card's activation. Two formats are supported:
     * date only (YYYY-MM-DD, treated as midnight UTC), or date with round hour
     * (YYYY-MM-DDTHH:mmZ in UTC, or YYYY-MM-DDTHH:mm±HH:mm with offset). Only round hours are
     * allowed when a time is provided (HH:00). The value must be at least the next round hour
     * after the request time.
     * <p>
     * [Optional]
     * </p>
     */
    @SerializedName("activation_date")
    private String activationDate;

    @SerializedName("card_product_id")
    private String cardProductId;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("activate_card")
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
