package com.checkout.payments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class PaymentMethodsDetails {

    @SerializedName("display_name")
    private String displayName;

    private String type;

    private String network;

}
