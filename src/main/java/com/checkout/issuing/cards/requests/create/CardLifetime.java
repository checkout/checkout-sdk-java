package com.checkout.issuing.cards.requests.create;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CardLifetime {

    private LifetimeUnit unit;

    private Integer value;

}