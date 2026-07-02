package com.checkout.issuing.cardholders;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CardholderAccessTokenResponse extends Resource {

    private String accessToken;

    private String tokenType;

    private Long expiresIn;

    private String scope;
}
