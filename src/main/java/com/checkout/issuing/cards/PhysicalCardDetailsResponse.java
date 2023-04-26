package com.checkout.issuing.cards;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PhysicalCardDetailsResponse extends AbstractCardDetailsResponse {

    protected PhysicalCardDetailsResponse() {
        super(CardType.PHYSICAL);
    }
}
