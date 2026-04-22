package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AccommodationRoom {

    /**
     * The room rate amount.
     * [Optional]
     */
    private String rate;

    /**
     * The number of nights at the specified room rate.
     * [Optional]
     */
    private String numberOfNightsAtRoomRate;

}
