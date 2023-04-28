package com.checkout.issuing.cards.requests.create;

import com.checkout.issuing.cards.CardType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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

    protected CardRequest(final CardType type) { this.type = type; }
}
