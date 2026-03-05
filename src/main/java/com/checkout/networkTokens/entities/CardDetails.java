package com.checkout.networkTokens.entities;

import lombok.Data;

@Data
public class CardDetails {

    private String last4;
    
    private String expiryMonth;
    
    private String expiryYear;

}