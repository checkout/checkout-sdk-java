package com.checkout.payments.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class PaymentSegment {
    
    private String brand;
    
    private String businessCategory;

    private String market;

}
