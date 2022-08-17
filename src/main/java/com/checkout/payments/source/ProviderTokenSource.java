package com.checkout.payments.source;

import com.checkout.common.AccountHolder;
import com.checkout.payments.RequestSource;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderTokenSource implements RequestSource {
    public static final String TYPE_NAME = "provider_token";
    private final String type = TYPE_NAME;

    @SerializedName("payment_method")
    private String paymentMethod;

    private String token;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    @Override
    public String getType() {
        return type;
    }
}
