package com.checkout.payments.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PaymentMethods {

    private String type;

    @SerializedName("card_schemes")
    private List<String> cardSchemes;

}
