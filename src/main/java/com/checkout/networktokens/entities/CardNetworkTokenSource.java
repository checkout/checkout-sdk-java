package com.checkout.networktokens.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class CardNetworkTokenSource extends AbstractNetworkTokenSource {

    private String number;
    
    private String expiryMonth;
    
    private String expiryYear;
    
    private String cvv;

    @Builder
    public CardNetworkTokenSource(final String number, final String expiryMonth, final String expiryYear, final String cvv) {
        super(NetworkTokenSourceType.CARD);
        this.number = number;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvv = cvv;
    }

}