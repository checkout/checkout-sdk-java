package com.checkout.payments.sessions;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Applepay {

    @SerializedName("store_payment_details")
    private StorePaymentDetailsType storePaymentDetails;

}