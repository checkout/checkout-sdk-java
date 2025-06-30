package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Room {

    /**
     * For lodging, contains the nightly rate for one room. For cruise, contains the total cost of the cruise.
     */
    private String rate;

    /**
     * For lodging, contains the number of nights charged at the rate provided in the rate field. For cruise, contains
     * the length of the cruise in days.
     */
    @SerializedName("number_of_nights_at_room_rate")
    private String numberOfNightsAtRoomRate;

}
