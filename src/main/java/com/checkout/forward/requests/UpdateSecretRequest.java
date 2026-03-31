package com.checkout.forward.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class UpdateSecretRequest {

    private String value;
    
    private String entityId;

}