package com.checkout.instruments;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInstrumentRequest {

    @SerializedName("expiry_month")
    private int expiryMonth;

    @SerializedName("expiry_year")
    private int expiryYear;

    private String name;

    @SerializedName("account_holder")
    private AccountHolder accountHolder;

    private Customer customer;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {

        private String id;

        @SerializedName("default")
        private boolean isDefault;
    }

}
