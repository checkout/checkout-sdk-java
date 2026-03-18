package com.checkout.networktokens.requests;

import com.checkout.networktokens.entities.DeletionReason;
import com.checkout.networktokens.entities.InitiatedBy;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteNetworkTokenRequest {

    private InitiatedBy initiatedBy;
    
    private DeletionReason reason;

}