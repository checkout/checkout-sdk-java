package com.checkout.sessions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public final class ThreeDsRequestorAuthenticationInfo {

    private ThreeDsReqAuthMethodType threeDsReqAuthMethod;

    private Instant threeDsReqAuthTimestamp;

    private String threeDsReqAuthData;

}
