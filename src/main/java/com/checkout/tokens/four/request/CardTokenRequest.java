package com.checkout.tokens.four.request;

import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.checkout.tokens.four.TokenType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public final class CardTokenRequest {

    private final TokenType type = TokenType.CARD;

    private String number;

    private Integer expiryMonth;

    private Integer expiryYear;

    private String name;

    private String ccv;

    @SerializedName("billing_address")
    private Address billingAddress;

    private Phone phone;

}
