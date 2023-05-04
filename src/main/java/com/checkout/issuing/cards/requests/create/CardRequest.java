package com.checkout.issuing.cards.requests.create;

import com.checkout.issuing.cards.CardType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class CardRequest {

    private final CardType type;

    @SerializedName("cardholder_id")
    private String cardholderId;

    private CardLifetime lifetime;

    private String reference;

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
