package com.checkout.networkTokens.responses;

import com.checkout.common.Resource;
import com.checkout.networkTokens.entities.CardDetails;
import com.checkout.networkTokens.entities.NetworkTokenDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NetworkTokenResponse extends Resource {

    private CardDetails card;
    
    private NetworkTokenDetails networkToken;
    
    private String tokenRequestorId;
    
    private String tokenSchemeId;

}