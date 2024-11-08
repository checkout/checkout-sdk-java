package com.checkout.payments.contexts;

import com.google.gson.annotations.SerializedName;
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

    @SerializedName("number_of_nights_at_room_rate")
    private Integer numberOfNightsAtRoomRate;
}
