package com.checkout.networktokens.entities;

import lombok.Data;

@Data
public final class CardDetails {

    private String last4;
    
    private String expiryMonth;
    
    private String expiryYear;

}