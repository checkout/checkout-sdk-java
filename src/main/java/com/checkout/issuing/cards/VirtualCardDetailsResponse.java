package com.checkout.issuing.cards;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VirtualCardDetailsResponse extends CardholderCardDetailsResponse {

    @SerializedName("is_single_use")
    private Boolean isSingleUse;

    public VirtualCardDetailsResponse() {
        super(CardType.VIRTUAL);
    }

    @Builder
    protected VirtualCardDetailsResponse(final Boolean isSingleUse) {
        super(CardType.VIRTUAL);
        this.isSingleUse = isSingleUse;
    }
}
