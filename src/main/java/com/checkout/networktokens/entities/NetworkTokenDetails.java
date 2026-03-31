package com.checkout.networktokens.entities;

import lombok.Data;

import java.time.Instant;

@Data
public final class NetworkTokenDetails {

    private String id;
    
    private NetworkTokenState state;
    
    private String number;
    
    private String expiryMonth;
    
    private String expiryYear;
    
    private NetworkTokenType type;
    
    private Instant createdOn;
    
    private Instant modifiedOn;
    
    private String paymentAccountReference;

}