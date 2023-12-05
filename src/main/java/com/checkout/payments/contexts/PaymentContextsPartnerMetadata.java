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
public final class PaymentContextsPartnerMetadata {

    @SerializedName("order_id")
    private String orderId;

    @SerializedName("customer_id")
    private String customerId;
}
