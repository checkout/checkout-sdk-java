package com.checkout.networkTokens.requests;

import com.checkout.networkTokens.entities.DeletionReason;
import com.checkout.networkTokens.entities.InitiatedBy;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteNetworkTokenRequest {

    private InitiatedBy initiatedBy;
    
    private DeletionReason reason;

}