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
    public VirtualCardRequest(
            Boolean isSingleUse,
            String cardholderId,
            CardLifetime lifetime,
            String reference,
            String cardProductId,
            String displayName,
            Boolean activateCard
    ) {
        super(CardType.VIRTUAL, cardholderId, lifetime, reference, cardProductId, displayName, activateCard);
        this.isSingleUse = isSingleUse;
    }

    public VirtualCardRequest() {
        super(CardType.VIRTUAL);
    }
}
