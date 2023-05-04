package com.checkout.issuing.cards.responses;

import com.checkout.issuing.cards.CardType;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PhysicalCardDetailsResponse extends CardDetailsResponse {

    public PhysicalCardDetailsResponse() {
        super(CardType.PHYSICAL);
    }
}
