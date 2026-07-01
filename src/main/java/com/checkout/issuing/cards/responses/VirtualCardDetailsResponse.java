package com.checkout.issuing.cards.responses;

import com.checkout.issuing.cards.CardType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class VirtualCardDetailsResponse extends CardDetailsResponse {

    private Boolean isSingleUse;

    public VirtualCardDetailsResponse() {
        super(CardType.VIRTUAL);
    }

}
