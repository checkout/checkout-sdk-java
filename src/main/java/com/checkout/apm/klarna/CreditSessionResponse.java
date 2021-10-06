package com.checkout.apm.klarna;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreditSessionResponse extends Resource {

    @SerializedName("session_id")
    private String sessionId;

    @SerializedName("client_token")
    private String clientToken;

    @SerializedName("payment_method_categories")
    private List<PaymentMethod> paymentMethodCategories;

}
