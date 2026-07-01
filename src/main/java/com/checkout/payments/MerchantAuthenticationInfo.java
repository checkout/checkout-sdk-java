package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class MerchantAuthenticationInfo {

    private String threeDsReqAuthMethod;
    
    private Instant threeDsReqAuthTimestamp;

    private String threeDsReqAuthData;

}
