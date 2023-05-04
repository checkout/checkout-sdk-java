package com.checkout.issuing.cards.requests.create;

import com.checkout.issuing.cards.CardType;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VirtualCardRequest extends CardRequest {

    @SerializedName("is_single_use")
    private Boolean isSingleUse;

    @Builder
    private VirtualCardRequest(final String cardholderId,
                               final CardLifetime lifetime,
                               final String reference,
                               final String cardProductId,
                               final String displayName,
                               final Boolean activateCard,
                               final Boolean isSingleUse) {
        super(CardType.VIRTUAL, cardholderId, lifetime, reference, cardProductId, displayName, activateCard);
        this.isSingleUse = isSingleUse;
    }
}