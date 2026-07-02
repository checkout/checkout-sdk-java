package com.checkout.payments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class InitialAuthentication {

    private String acsTransactionId;

    private ThreeDSAuthenticationMethod authenticationMethod;
    
    private String authenticationTimestamp;

    private String authenticationData;

    private String initialSessionId;
}
