package com.checkout.payments.contexts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentContextsAccommodationRoom {

    private String rate;

    private Integer numberOfNightsAtRoomRate;
}
