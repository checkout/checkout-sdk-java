package com.checkout.instruments.previous;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateInstrumentRequest {

    private int expiryMonth;

    private int expiryYear;

    private String name;

    private InstrumentAccountHolder accountHolder;

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
