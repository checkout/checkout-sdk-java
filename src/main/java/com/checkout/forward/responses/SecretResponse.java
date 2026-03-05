package com.checkout.forward.responses;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
public class SecretResponse extends Resource {

    private String name;
    
    private Instant createdAt;
    
    private Instant updatedAt;
    
    private Integer version;
    
    private String entityId;

}