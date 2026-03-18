package com.checkout.forward.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSecretRequest {

    private String name;
    
    private String value;
    
    private String entityId;

}