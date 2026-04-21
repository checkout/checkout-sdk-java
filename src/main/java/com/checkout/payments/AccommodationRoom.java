package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("number_of_nights_at_room_rate")
    private String numberOfNightsAtRoomRate;

}
