package com.checkout.issuing.cards.responses;

import com.checkout.issuing.cards.CardType;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VirtualCardDetailsResponse extends AbstractCardDetailsResponse {

    @SerializedName("is_single_use")
    private Boolean isSingleUse;

    protected VirtualCardDetailsResponse() {
        super(CardType.VIRTUAL);
    }

    @Builder
    protected VirtualCardDetailsResponse(Boolean isSingleUse) {
        super(CardType.VIRTUAL);
        this.isSingleUse = isSingleUse;
    }
}
