package com.checkout.tokens.beta;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class CardTokenRequest {

    private final String type = "card";

    private String number;

    private Integer expiryMonth;

    private Integer expiryYear;

    private String name;

}
