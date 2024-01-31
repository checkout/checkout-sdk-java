package com.checkout.payments.request;

import com.google.gson.annotations.SerializedName;
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
    
    @SerializedName("business_category")
    private String businessCategory;

    private String market;

}
