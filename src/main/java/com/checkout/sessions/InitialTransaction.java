package com.checkout.sessions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class InitialTransaction {
    
    private String acsTransactionId;

    private String authenticationMethod;

    private String authenticationTimestamp;

    private String authenticationData;

    private String initialSessionId;
    
}
