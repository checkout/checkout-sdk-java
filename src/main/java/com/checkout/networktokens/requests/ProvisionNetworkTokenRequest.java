package com.checkout.networktokens.requests;

import com.checkout.networktokens.entities.AbstractNetworkTokenSource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ProvisionNetworkTokenRequest {

    private AbstractNetworkTokenSource source;

}