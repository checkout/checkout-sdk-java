package com.checkout.networktokens.responses;

import com.checkout.common.Resource;
import com.checkout.networktokens.entities.CardDetails;
import com.checkout.networktokens.entities.NetworkTokenDetails;

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