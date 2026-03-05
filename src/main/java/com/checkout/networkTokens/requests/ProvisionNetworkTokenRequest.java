package com.checkout.networkTokens.requests;

import com.checkout.networkTokens.requests.sources.AbstractNetworkTokenSource;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProvisionNetworkTokenRequest {

    private AbstractNetworkTokenSource source;

}