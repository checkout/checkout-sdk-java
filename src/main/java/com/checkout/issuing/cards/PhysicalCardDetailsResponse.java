package com.checkout.issuing.cards;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PhysicalCardDetailsResponse extends CardholderCardDetailsResponse {

    public PhysicalCardDetailsResponse() {
        super(CardType.PHYSICAL);
    }
}
