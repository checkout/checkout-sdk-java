package com.checkout.issuing.cards.responses;

import com.checkout.issuing.cards.CardType;
import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VirtualCardDetailsResponse extends CardDetailsResponse {

    @SerializedName("is_single_use")
    private Boolean isSingleUse;

    public VirtualCardDetailsResponse() {
        super(CardType.VIRTUAL);
    }

}
