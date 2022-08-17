package com.checkout.payments.source;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.checkout.payments.NetworkTokenType;
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
public class NetworkTokenSource implements RequestSource {

    public static final String TYPE_NAME = "network_token";

    private static final String type = TYPE_NAME;

    private String token;

    @SerializedName("expiry_month")
    private Integer expiryMonth;

    @SerializedName("expiry_year")
    private Integer expiryYear;

    @SerializedName("token_type")
    private NetworkTokenType tokenType;

    private String cryptogram;

    private String eci;

    private Boolean stored;

    private String name;

    private String cvv;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

    @Override
    public String getType() {
        return type;
    }
}
